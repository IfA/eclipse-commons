/**
 *
 */
package de.tud.et.ifa.agtele.ui.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.SourceRange;
import org.eclipse.jdt.internal.core.SourceMethod;
import org.eclipse.jdt.internal.core.SourceType;
import org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor;
import org.eclipse.jdt.internal.ui.javaeditor.EditorUtility;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;

import de.tud.et.ifa.agtele.ui.AgteleUIPlugin;
import de.tud.et.ifa.agtele.ui.handlers.PushCodeToEcoreHandler;
import de.tud.et.ifa.agtele.ui.util.UIHelper;

/**
 * A {@link WorkspaceCommandListener} that listens to 'save' events in {@link CompilationUnitEditor Java editors}. If
 * generated Java code (tagged with '@generated') was changed by the user, this reminds the user to change the
 * annotation to '@generated NOT'.
 * <p />
 * Note: The listener has to be initialized via {@link #init()}.
 * <p />
 * Note: Currently, only changes to generated methods are addressed. However, it could maybe be useful to also handled
 * changes to fields or class declarations in the future.
 * <p />
 * Note: The listener can work in various {@link GeneratedCodeChangedListenerMode modes} that can be enabled via
 * {@link #setMode(GeneratedCodeChangedListenerMode)}. Specified modes are persisted in the {@link IPreferenceStore} of
 * the {@link AgteleUIPlugin} by use of the {@link #OPERATING_MODE_SETTING_KEY}.
 *
 *
 * @author mfreund
 */
@SuppressWarnings("restriction")
public class GeneratedCodeChangedListener extends WorkspaceCommandListener {

	/**
	 * The singleton instance of this listener.
	 */
	protected static GeneratedCodeChangedListener instance;

	/**
	 * The {@link GeneratedCodeChangedListenerMode} that the listener currently operates on.
	 */
	private static GeneratedCodeChangedListenerMode mode;

	/**
	 * The {@link IPreferenceStore} of the {@link AgteleUIPlugin}.
	 */
	protected static IPreferenceStore store = null;

	/**
	 * The settings key for the {@link IPreferenceStore}.
	 */
	protected static final String OPERATING_MODE_SETTING_KEY = "GENERATED_CODE_CHANGED_LISTENER_OPERATING_MODE";

	/**
	 * A regex that can be used to check if the JavaDoc of a method is equipped with the '@generated' tag. Also, it can
	 * be used to separate the JavaDoc (group 1) of the actual source (group 2) of a Java element.
	 */
	protected static final String JAVADOC_WITH_GENERATED_TAG_REGEX = "(/\\*\\*[\\s\\S]*" + // JavaDoc beginning
			"@generated(?![\\s]+NOT)" + // '@generated' Tag (not followed by 'NOT')
			"[\\s\\S]*\\*/)" // JavaDoc ending
			+ "([\\s\\S]+)"; // Actual content of the method

	/**
	 * The {@link Pattern} for the {@link #JAVADOC_WITH_GENERATED_TAG_REGEX}.
	 */
	protected final Pattern javaDocPattern = Pattern
			.compile(GeneratedCodeChangedListener.JAVADOC_WITH_GENERATED_TAG_REGEX);

	/**
	 * Initializes the singleton {@link #instance} of this listener and registers it to the {@link ICommandService}.
	 *
	 */
	public static void init() {

		if (GeneratedCodeChangedListener.instance != null) {

			// The listener was already initialized
			//
			return;
		}

		GeneratedCodeChangedListener.instance = new GeneratedCodeChangedListener();

		IWorkbench wb = PlatformUI.getWorkbench();

		final ICommandService commandService = wb.getService(ICommandService.class);

		commandService.addExecutionListener(new GeneratedCodeChangedListener());
	}

	/**
	 * This creates an instance.
	 *
	 */
	protected GeneratedCodeChangedListener() {

		// Only listen to 'save' events in the Java editor
		//
		super(Arrays.asList(IWorkbenchCommandConstants.FILE_SAVE, IWorkbenchCommandConstants.FILE_SAVE_AS,
				IWorkbenchCommandConstants.FILE_SAVE_ALL), Arrays.asList(CompilationUnitEditor.class));
	}

	/**
	 * Listen to the {@link IExecutionListener#preExecute(String, ExecutionEvent) preExecute} event so that we can make
	 * changes to the file before it is actually being saved.
	 */
	@Override
	protected void doPreExecute(String commandId, ExecutionEvent event) {

		if (GeneratedCodeChangedListener.getMode() == GeneratedCodeChangedListenerMode.OFF) {
			return;
		}

		IEditorPart currentEditor = UIHelper.getCurrentEditor();
		ISelection currentSelection = UIHelper.getCurrentSelection();

		if (commandId.equals(IWorkbenchCommandConstants.FILE_SAVE)) {

			// Only handle the current editor
			//
			this.handleEditorBeforeSave(currentEditor);

		} else if (commandId.equals(IWorkbenchCommandConstants.FILE_SAVE_ALL)) {

			// Handle all open editors
			//
			for (IEditorPart editor : UIHelper.getAllEditors()) {

				if (GeneratedCodeChangedListener.getMode() == GeneratedCodeChangedListenerMode.USER) {

					// Bring the editor to the top to allow the user identify the changes
					//
					UIHelper.activateEditor(editor);
				}

				this.handleEditorBeforeSave(editor);
			}

		}

		// Reset the original UI state (bring the 'currentEditor' back to the top and reset the 'currentSelection')
		//
		UIHelper.activateEditor(currentEditor);
		if (currentEditor instanceof ISelectionProvider) {
			((ISelectionProvider) currentEditor).setSelection(currentSelection);
		}
	}

	/**
	 * This will do the actual work: If the given {@link IEditorPart editor} is a Java editor, check if any methods that
	 * are tagged with '@generated' have been {@link #getChangedMethodsWithGeneratedTag(ITypeRoot, ITextFileBuffer)
	 * changed} and, if this is the case, {@link #handleChangedMethodsWithGeneratedTag(List, ITextFileBuffer) react}
	 * accordingly.
	 *
	 * @param editor
	 *            The {@link IEditorPart editor} that shall be handled.
	 */
	protected void handleEditorBeforeSave(IEditorPart editor) {

		if (!(editor instanceof CompilationUnitEditor) || ((CompilationUnitEditor) editor).getViewer() == null) {
			return;
		}

		// In order to prevent manual parsing of the Java document, we make use of the CompilationUnit type that
		// represents a structured Java document
		//
		ITypeRoot root = EditorUtility.getEditorInputJavaElement(editor, false);

		if (!(root instanceof ICompilationUnit)) {

			// No Java source file is edited
			//
			return;
		}

		try {

			ITextFileBufferManager fileBufferManager = FileBuffers.getTextFileBufferManager();

			// Normally, the manager should already be connected to the location (because the file is opened in the
			// editor). But just to be sure, we connect again (will not do anything if already connected).
			//
			fileBufferManager.connect(root.getPath(), LocationKind.NORMALIZE, null);

			// The TextFileBuffer that knows about all changes to the document
			//
			ITextFileBuffer textFileBuffer = fileBufferManager.getTextFileBuffer(root.getPath(),
					LocationKind.NORMALIZE);

			if (!textFileBuffer.isDirty()) {
				// Nothing to do as there are no changes that we need to check
				//
				return;
			}

			// All methods that are tagged with '@generated' and have been changed by the user since the last save
			//
			List<SourceMethod> changedMethodsWithGeneratedTag = this.getChangedMethodsWithGeneratedTag(root,
					textFileBuffer);

			// Add 'NOT' to the '@generated' tag if necessary
			//
			this.handleChangedMethodsWithGeneratedTag(changedMethodsWithGeneratedTag, textFileBuffer);

		} catch (Exception e) {

			AgteleUIPlugin.getPlugin().getLog().log(new Status(Status.ERROR, "de.tud.et.ifa.agtele.eclipse.commons.ui",
					e.getMessage() != null ? e.getMessage() : e.toString(), e));
		}
	}

	/**
	 * Determine all {@link SourceMethod methods} inside the given {@link ITypeRoot} that 1. are tagged with
	 * '@generated' and 2. have been changed since the last save.
	 * <p />
	 * Note: This uses
	 * {@link EditorUtility#calculateChangedLineRegions(ITextFileBuffer, org.eclipse.core.runtime.IProgressMonitor)} to
	 * determine the changed regions. This however does not report deleted lines as changes but only added or changed
	 * lines!
	 *
	 * @param typeRoot
	 *            The {@link ITypeRoot} representing the logical model of the Java file whose contents shall be
	 *            analyzed.
	 * @param textFileBuffer
	 *            The {@link ITextFileBuffer} buffering the contents of the Java file representing the given
	 *            <em>typeRoot</em>.
	 * @return A list of changed {@link SourceMethod methods} tagged with '@generated'.
	 * @throws CoreException
	 *             If an exception occurs while accessing the resource corresponding to the given buffer.
	 */
	protected List<SourceMethod> getChangedMethodsWithGeneratedTag(ITypeRoot typeRoot, ITextFileBuffer textFileBuffer)
			throws CoreException {

		// This represents the main class inside the Java document
		//
		Optional<SourceType> sourceType = Arrays.asList(typeRoot.getChildren()).parallelStream()
				.filter(j -> j instanceof SourceType).map(j -> (SourceType) j).findFirst();

		if (!sourceType.isPresent()) {
			return new ArrayList<>();
		}

		// The list of Java methods that are tagged with '@generated' (but not with '@generate NOT')
		//
		List<SourceMethod> methodsWithGeneratedTag = Arrays.asList(sourceType.get().getMethods()).stream()
				.filter(m -> m instanceof SourceMethod).map(m -> (SourceMethod) m).filter(this::isTaggedWithGenerated)
				.collect(Collectors.toList());

		if (methodsWithGeneratedTag.isEmpty()) {
			// Nothing to be done as there are no methods tagged with '@generated'
			//
			return new ArrayList<>();
		}

		// The list of regions that have been changed since the last save.
		//
		List<IRegion> changedRegions = Arrays
				.asList(EditorUtility.calculateChangedLineRegions(textFileBuffer, new NullProgressMonitor()));

		if (changedRegions.isEmpty()) {
			// Nothing to be done as there are changed regions. This should usually not happen as we checked that
			// the editor is dirty above.
			//
			return new ArrayList<>();
		}

		// Check if any changes have been made to methods that are tagged with '@generated'
		//
		return methodsWithGeneratedTag.stream().filter(m -> this.hasMethodBeenChanged(m, changedRegions))
				.collect(Collectors.toList());
	}

	/**
	 * Whether the given {@link IMethod} is tagged with '@generated' (but not with '@generated NOT').
	 *
	 * @param method
	 *            The {@link IMethod} to check.
	 * @return '<em>true</em>' if the given {@link IMethod} is tagged with '@generated'.
	 */
	protected boolean isTaggedWithGenerated(IMethod method) {

		try {
			// Theoretically, it should be better to only query '#getJavadocRange'. However, this seems to sometimes
			// return 'null' although there is a JavaDoc for the method.
			//
			String source = ((SourceMethod) method).getSource();

			return source != null && this.javaDocPattern.matcher(source).matches();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Whether the given {@link SourceMethod} has been changed since the last save.
	 *
	 * @param changedRegions
	 *            The list of changed {@link IRegion regions}.
	 * @param method
	 *            The {@link IMethod} to check.
	 *
	 * @return '<em>true</em>' if the given {@link IMethod} has been changed.
	 */
	protected boolean hasMethodBeenChanged(SourceMethod method, List<IRegion> changedRegions) {

		try {

			ISourceRange sourceRange = method.getSourceRange();

			// We are not interested in changes to the JavaDoc of a method but only in changes to the actual source. In
			// order to determine this, we use the 'javaDocPattern' from above.
			//
			Matcher matcher = this.javaDocPattern.matcher(method.getSource());

			if (!matcher.matches()) {
				// This should not happen because we have checked this before as part of 'isTaggedWithGenerated'
				//
				throw new RuntimeException("Internal Error while determining change methods in the Java source file!");
			}

			String javaDoc = matcher.group(1);

			SourceRange sourceRangeWithoutJavadoc = new SourceRange(sourceRange.getOffset() + javaDoc.length(),
					sourceRange.getLength() - javaDoc.length());

			// Check if any of the changed regions overlap with the determined range of the method source
			//
			return changedRegions.parallelStream()
					.anyMatch(cr -> sourceRangeWithoutJavadoc.getOffset() <= cr.getOffset()
							&& cr.getOffset() <= sourceRangeWithoutJavadoc.getOffset()
									+ sourceRangeWithoutJavadoc.getLength()
							|| cr.getOffset() <= sourceRangeWithoutJavadoc.getOffset()
									&& sourceRangeWithoutJavadoc.getOffset() <= cr.getOffset() + cr.getLength());

		} catch (JavaModelException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Determine all {@link SourceMethod methods} inside the given {@link ITypeRoot} that 1. are tagged with
	 * '@generated' and 2. have been changed since the last save.
	 *
	 * @param methods
	 *            A list of changed {@link SourceMethod methods} tagged with '@generated'.
	 * @param textFileBuffer
	 *            The {@link ITextFileBuffer} buffering the contents of the Java file representing the given
	 *            <em>methods</em>.
	 * @throws JavaModelException
	 *             If an exception occurs while accessing the resource corresponding to the given buffer.
	 * @throws BadLocationException
	 */
	protected void handleChangedMethodsWithGeneratedTag(List<SourceMethod> methods, ITextFileBuffer textFileBuffer)
			throws JavaModelException, BadLocationException {

		if (methods.isEmpty()) {
			// Nothing to be done
			//
			return;
		}

		for (int i = 0; i < methods.size(); i++) {

			SourceMethod sourceMethod = methods.get(i);

			// Assume we are in GeneratedCodeChangedListenerMode#AUTOMATIC mode
			//
			boolean addNotTag = true;
			Optional<String> explanation = Optional.empty();
			boolean doNotAskAnyMore = true;

			if (GeneratedCodeChangedListener.getMode() == GeneratedCodeChangedListenerMode.USER) {

				StringBuilder methodIdentifier = new StringBuilder(sourceMethod.getElementName()).append("(");
				if (sourceMethod.getNumberOfParameters() > 0) {
					methodIdentifier.append("...");
				}
				methodIdentifier.append(")");

				// If we are instead in USER mode, present a dialog to the user
				//
				AddNotToGeneratedTagDialog dialog = new AddNotToGeneratedTagDialog(methodIdentifier.toString(),
						methods.size() - i - 1);
				dialog.create();

				int result = dialog.open();

				explanation = dialog.getAddNotTagExplanation();
				doNotAskAnyMore = dialog.isDoNotAskAnyMore();
				addNotTag = result == Window.OK;
			}

			// The user chose 'No' -> do nothing
			//
			if (!addNotTag) {
				if (doNotAskAnyMore) {
					break;
				} else {
					continue;
				}
			}

			// The user chose 'Yes' -> add NOT tag
			//
			// FIXME also enable 'pushing to ecore' and 'creating Gen method' instead
			if (doNotAskAnyMore) {
				for (SourceMethod m : methods.subList(i, methods.size())) {
					this.addNotTag(m, textFileBuffer, explanation);
				}
				break;

			} else {
				this.addNotTag(sourceMethod, textFileBuffer, explanation);
			}
		}
	}

	/**
	 * Add a 'NOT' to the '@generated' tag of the given {@link SourceMethod}.
	 *
	 * @param method
	 *            The {@link SourceMethod} to which the 'NOT' shall be added.
	 * @param textFileBuffer
	 *            The {@link ITextFileBuffer} buffering the contents of the Java file representing the given
	 *            <em>method</em>.
	 * @param explanation
	 *            An optional additional String that will be appended to the 'NOT' explaining while the generated method
	 *            body was changed.
	 */
	protected void addNotTag(SourceMethod method, ITextFileBuffer textFileBuffer, Optional<String> explanation)
			throws JavaModelException, BadLocationException {

		String oldSource = method.getSource();
		method.getJavaModel().getChildren();

		String newSource = oldSource.replaceFirst("@generated",
				"@generated NOT" + (explanation.isPresent() ? " " + explanation : ""));

		textFileBuffer.getDocument().replace(method.getSourceRange().getOffset(), method.getSourceRange().getLength(),
				newSource);

	}

	/**
	 * Invoke the {@link PushCodeToEcoreHandler}.
	 * <p />
	 * Note: The 'pushToEcore' handler is invoked asynchronously in order not to interrupt the current save process.
	 *
	 * @param method
	 *            The {@link SourceMethod} to which the 'NOT' shall be added.
	 * @param textFileBuffer
	 *            The {@link ITextFileBuffer} buffering the contents of the Java file representing the given
	 *            <em>method</em>.
	 */
	protected void pushToEcore(SourceMethod method, ITextFileBuffer textFileBuffer) {

		UIHelper.getShell().getDisplay().asyncExec(() -> {

			try {

				new PushCodeToEcoreHandler().execute(new ExecutionEvent());

			} catch (ExecutionException e) {
				AgteleUIPlugin.getPlugin().getLog()
						.log(new Status(Status.ERROR, "de.tud.et.ifa.agtele.eclipse.commons.ui",
								e.getMessage() != null ? e.getMessage() : e.toString(), e));
			}
		});
	}

	@Override
	protected void doNotHandled(String commandId, NotHandledException exception) {

		// nothing to be done
		//
	}

	@Override
	protected void doPostExecuteFailure(String commandId, ExecutionException exception) {

		// nothing to be done
		//
	}

	@Override
	protected void doPostExecuteSuccess(String commandId, Object returnValue) {

		// nothing to be done
		//
	}

	/**
	 * @return the {@link #mode}
	 */
	public static GeneratedCodeChangedListenerMode getMode() {

		if (GeneratedCodeChangedListener.mode == null) {
			// Read the mode from the preferences
			//
			if (GeneratedCodeChangedListener.store == null) {
				GeneratedCodeChangedListener.store = AgteleUIPlugin.getPlugin().getPreferenceStore();
			}
			int storedModeValue = GeneratedCodeChangedListener.store
					.getInt(GeneratedCodeChangedListener.OPERATING_MODE_SETTING_KEY);
			GeneratedCodeChangedListenerMode storedMode = GeneratedCodeChangedListenerMode.getMode(storedModeValue);

			GeneratedCodeChangedListener.mode = storedMode != null ? storedMode : GeneratedCodeChangedListenerMode.USER;
		}

		return GeneratedCodeChangedListener.mode;
	}

	/**
	 * This sets the new {@link GeneratedCodeChangedListenerMode}.
	 *
	 * @param mode
	 *            the {@link GeneratedCodeChangedListenerMode} to set
	 */
	public static void setMode(GeneratedCodeChangedListenerMode mode) {

		if (GeneratedCodeChangedListener.store == null) {
			GeneratedCodeChangedListener.store = AgteleUIPlugin.getPlugin().getPreferenceStore();
		}

		GeneratedCodeChangedListener.mode = mode;
		GeneratedCodeChangedListener.store.setValue(GeneratedCodeChangedListener.OPERATING_MODE_SETTING_KEY,
				mode.getValue());
	}

	/**
	 * This sets the new {@link GeneratedCodeChangedListenerMode}.
	 *
	 * @param modeValue
	 *            the value of the {@link GeneratedCodeChangedListenerMode} to set
	 */
	public static void setMode(int modeValue) {

		GeneratedCodeChangedListener.setMode(GeneratedCodeChangedListenerMode.getMode(modeValue));

	}

	/**
	 * An enum describing the different operating modes of the {@link GeneratedCodeChangedListener}.
	 *
	 * @author mfreund
	 */
	public enum GeneratedCodeChangedListenerMode {

		/**
		 * The listener works in user mode (the user is asked whether NOT tags should be added via a dialog).
		 */
		USER(0),

		/**
		 * The listener works in automatic mode (NOT tags are added automatically).
		 */
		AUTOMATIC(1),

		/**
		 * The listener is turned off.
		 */
		OFF(2);

		private final int value;

		private GeneratedCodeChangedListenerMode(final int value) {

			this.value = value;
		}

		/**
		 * Returns the integer value of the enum constant.
		 *
		 * @return The integer value of the enum constant.
		 */
		public int getValue() {

			return this.value;
		}

		/**
		 * Returns the mode for the specified value.
		 *
		 * @param value
		 *            the int value of the mode to return
		 * @return The {@link GeneratedCodeChangedListenerMode} or '<em>null</em>' if no mode for the specified value
		 *         exists.
		 */
		public static GeneratedCodeChangedListenerMode getMode(int value) {

			if (value == 0) {
				return USER;
			} else if (value == 1) {
				return GeneratedCodeChangedListenerMode.AUTOMATIC;
			} else if (value == 2) {
				return GeneratedCodeChangedListenerMode.OFF;
			} else {
				throw new IllegalArgumentException(
						"The value " + value + " does not represent a valid GeneratedCodeChangedListenerMode!");
			}
		}
	}

	/**
	 * A {@link Dialog} that allows the user to choose whether the '@generated' tag of a manually changed method shall
	 * be changed to '@generated NOT'.
	 *
	 * @author mfreund
	 */
	public class AddNotToGeneratedTagDialog extends TitleAreaDialog {

		/**
		 * A {@link Text} that allows the user to enter an explanation text why the generated method was changed
		 * manually.
		 */
		protected Text addNotTagExplanationText;

		/**
		 * The explanation entered by the user in the {@link #addNotTagExplanationText}.
		 */
		protected String addNotTagExplanation;

		/**
		 * A {@link Button} that allows the user to prevent further inquiries during the current save action.
		 */
		protected Button doNotAskAnyMoreButton;

		/**
		 * A boolean indicating whether the user checked the {@link #doNotAskAnyMoreButton}.
		 */
		protected boolean doNotAskAnyMore;

		/**
		 * A String that allows the user to identify the method that is the subject of this dialog.
		 */
		protected String methodIdentifier;

		/**
		 * An Integer indicating the number of further inquiries that will happen during the current save action (the
		 * rest of the changed methods).
		 */
		protected int pendingRequests;

		/**
		 * This creates an instance.
		 *
		 * @param methodIdentifier
		 *            A String that allows the user to identify the method that is the subject of this dialog.
		 * @param pendingRequests
		 *            An Integer indicating the number of further inquiries that will happen during the current save
		 *            action (the rest of the changed methods).
		 */
		public AddNotToGeneratedTagDialog(String methodIdentifier, int pendingRequests) {

			super(UIHelper.getShell());

			this.methodIdentifier = methodIdentifier;
			this.pendingRequests = pendingRequests;

			this.addNotTagExplanation = "";
			this.doNotAskAnyMore = false;
		}

		@Override
		public void create() {

			super.create();
			this.setTitle("Method '" + this.methodIdentifier + "' was changed!");
			this.setMessage("Change '@generated' tag to '@generated NOT'?", IMessageProvider.WARNING);
		}

		@Override
		protected void createButtonsForButtonBar(Composite parent) {

			// create OK and Cancel buttons (as default) but change their labels
			this.createButton(parent, IDialogConstants.OK_ID, "Yes (Add NOT tag)", true);
			this.createButton(parent, IDialogConstants.CANCEL_ID, "No", false);
		}

		@Override
		protected Control createDialogArea(Composite parent) {

			Composite area = (Composite) super.createDialogArea(parent);
			Composite container = new Composite(area, SWT.NONE);
			container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			GridLayout layout = new GridLayout(2, false);
			layout.verticalSpacing = 8;
			layout.marginRight = 5;
			layout.marginLeft = 5;
			container.setLayout(layout);

			Label lblAdditionalTextoptional = new Label(container, SWT.NONE);
			lblAdditionalTextoptional.setToolTipText("An explanation why this method was changed manually");
			lblAdditionalTextoptional.setText("Additional text (optional):");

			this.addNotTagExplanationText = new Text(container, SWT.BORDER);
			this.addNotTagExplanationText.setToolTipText("An explanation why this method was changed manually");
			this.addNotTagExplanationText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

			if (this.pendingRequests > 0) {
				this.doNotAskAnyMoreButton = new Button(container, SWT.CHECK);
				this.doNotAskAnyMoreButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
				this.doNotAskAnyMoreButton
						.setText("Remember my decision (" + this.pendingRequests + " additional changes)");
			}

			Label lblInfo = new Label(container, SWT.WRAP);
			lblInfo.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, true, true, 2, 1));
			lblInfo.setText(
					"Note: This dialog can be disabled via \"Agtele Settings -> Add 'NOT' to changed '@generated' methods\"!");

			return area;
		}

		@Override
		protected boolean isResizable() {

			return true;
		}

		// save content of the UI elements because they get disposed
		// as soon as the Dialog closes
		protected void saveInput() {

			this.addNotTagExplanation = this.addNotTagExplanationText.getText();
			this.doNotAskAnyMore = this.doNotAskAnyMoreButton != null && this.doNotAskAnyMoreButton.getSelection();
		}

		@Override
		protected void cancelPressed() {

			this.saveInput();
			super.cancelPressed();
		}

		@Override
		protected void okPressed() {

			this.saveInput();
			super.okPressed();
		}

		/**
		 * The text that the user entered as additional explanation or an empty optional if nothing was entered.
		 *
		 * @return the {@link #addNotTagExplanation}
		 */
		public Optional<String> getAddNotTagExplanation() {

			return this.addNotTagExplanation.isEmpty() ? Optional.empty() : Optional.of(this.addNotTagExplanation);
		}

		/**
		 * Whether the user selected the {@link #doNotAskAnyMore} checkbox.
		 * <p />
		 * Note: This will return <em>false</em> if {@link #pendingRequests} was set to null in the
		 * {@link #AddNotToGeneratedTagDialog(String, int) constructor}.
		 *
		 * @return the {@link #doNotAskAnyMore}
		 */
		public boolean isDoNotAskAnyMore() {

			return this.doNotAskAnyMore;
		}

	}
}
