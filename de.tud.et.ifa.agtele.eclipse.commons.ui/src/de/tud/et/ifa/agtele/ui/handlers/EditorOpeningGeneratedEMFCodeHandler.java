/**
 *
 */
package de.tud.et.ifa.agtele.ui.handlers;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;

import de.tud.et.ifa.agtele.ui.editors.AgteleEcoreEditor;
import de.tud.et.ifa.agtele.ui.util.UIHelper;

/**
 * An {@link AbstractGeneratedEMFCodeHandler} that opens the Ecore metamodel associated with a Java file in an editor.
 *
 * @author mfreund
 */
public abstract class EditorOpeningGeneratedEMFCodeHandler extends AbstractGeneratedEMFCodeHandler {

	/**
	 * The editor that was opened by this handled during {@link #doExecute()} to present the Ecore metamodel to the
	 * user.
	 */
	protected IEditorPart ecoreEditor;

	@Override
	protected void doExecute() throws Exception {

		this.ecoreEditor = this.openEcoreEditor(this.helper.getEcoreFile());

		if (this.ecoreEditor == null) {
			return;
		}

		try {
			this.performSyncAction();
		} catch (Exception e) {
			UIHelper.openEditor(this.helper.getJavaFile());
			throw e;
		}

		// If we had to open a new editor instead of being able to reuse an existing one, the editor might not be
		// completely opened yet. Thus, we give clients the change to perform any work relying on an initialized editor
		// in an asynchronous manner.
		//
		UIHelper.getShell().getDisplay().asyncExec(() -> {
			try {
				this.performAsyncActionOnEcoreEditor();
			} catch (Exception e) {

				this.showError(e.getMessage());
				UIHelper.log(e);

			}
		});

		return;

	}

	/**
	 * Overwriting this allows clients to perform any actions after {@link #openEcoreEditor(IFile) opening the editor}.
	 * <p />
	 * Note: {@link #openEcoreEditor(IFile) Opening the editor} does not mean that the editor is fully initialized, so
	 * that some actions like selecting elements would fail when called directly after opening the editor. If such
	 * action shall be performed, consider overwriting {@link #performAsyncActionOnEcoreEditor()} instead.
	 *
	 * @see #performAsyncActionOnEcoreEditor()
	 *
	 *      ${tags}
	 */
	protected void performSyncAction() throws Exception {

		// The default implementation does nothing
		//
	}

	/**
	 * {@link #openEcoreEditor(IFile) Opening the editor} does not mean that the editor is fully initialized, so that
	 * some actions like selecting elements would fail when called directly after opening the editor. Thus,
	 * {@link #doExecute()} executes this method asynchronously so that clients can override this method to perform any
	 * additional work that relies on a fully initialized editor in an asynchronous manner.
	 *
	 * @throws Exception
	 *             If anything goes wrong.
	 */
	protected void performAsyncActionOnEcoreEditor() throws Exception {

		// The default implementation does nothing
		//
	}

	/**
	 * Tries to open the default editor for the specified ecore file. If the opening fails, the method displays an error
	 * using {@link #showError(String)}. The editor is accepted only, if it is an {@link IEditingDomainProvider}. If the
	 * default editor is not capable to use, the {@link AgteleEcoreEditor} will be opened.
	 *
	 * @param ecoreFile
	 * @return the opened ecore editor
	 */
	protected IEditorPart openEcoreEditor(IFile ecoreFile) {

		List<IEditorPart> openEditors = UIHelper.getAllEditors();

		// This will be returned in the end.
		//
		IEditorPart editor;

		try {
			editor = UIHelper.openEditor(ecoreFile);

			if (!(editor instanceof IEditingDomainProvider)) {
				if (!openEditors.contains(editor)) {
					editor.getEditorSite().getPage().closeEditor(editor, false);
				}

				editor = UIHelper.openEditor(ecoreFile, "de.tud.et.ifa.agtele.ui.editors.EcoreEditorID");
			}
		} catch (PartInitException e1) {
			throw new RuntimeException("Unable to open editor for associated Ecore metamodel!", e1);
		}

		if (!(editor instanceof AgteleEcoreEditor)) {
			throw new RuntimeException("Unable to open compatible ecore editor!");
		}

		return editor;
	}

}
