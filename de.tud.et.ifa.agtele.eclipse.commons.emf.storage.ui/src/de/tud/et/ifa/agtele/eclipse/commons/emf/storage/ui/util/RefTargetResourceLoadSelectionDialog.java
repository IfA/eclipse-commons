package de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.wb.swt.SWTResourceManager;

import de.tud.et.ifa.agtele.eclipse.commons.emf.storage.ui.EMFStorageUIPlugin;

public class RefTargetResourceLoadSelectionDialog extends Dialog {
	
	public static abstract class Result {
		protected String selectedResource = null;
		protected boolean isCancelled = false;
		protected boolean openInCurrentEditor = false;
		protected boolean openInNewEditor = false;
		protected java.util.List<String> resourcesToRemove = new ArrayList<>();
		
		public String getSelectedResource () {
			return this.selectedResource;
		}		
		public boolean isCancelled () {
			return this.isCancelled;
		}
		public boolean isOpenInNewEditor () {
			return this.openInNewEditor;
		}
		public boolean isOpenInCurrentEditor () {
			return this.openInCurrentEditor;
		}
		public java.util.List<String> getResourcesToRemove() {
			return new ArrayList<>(this.resourcesToRemove);
		}
		public abstract void dialogClosed();
	}
		
	static protected RefTargetResourceLoadSelectionDialog currentInstance = null;
	static protected Point lastSize = null;
	protected Text explanationText;
	protected LinkedHashMap<IFile, String> options = new LinkedHashMap<>();
	protected Result result = null;
	protected Button openInNewEditor;
	protected Button openInCurrentEditor;
	protected Button cancelButton;
	protected TableViewer listViewer;
	protected Table tableView;
	protected IWorkspaceRoot root;
	protected Composite removeComposite;
	protected Button removeButton;
	protected Button removeAllButton;
	protected Composite openComposite;
	protected Text removeExplanationText;
	protected IFile currentSelection = null;
	
	/**
	 * @wbp.parser.constructor
	 */
	private RefTargetResourceLoadSelectionDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.CLOSE | SWT.MODELESS | SWT.BORDER | SWT.TITLE | SWT.RESIZE);
        setBlockOnOpen(false);
        
        if (currentInstance != null) {
        	currentInstance.close();
        }
        currentInstance = this;
	}
	
	public RefTargetResourceLoadSelectionDialog(Set<String> options, Result result) {
		this(null);
		this.root = ResourcesPlugin.getWorkspace().getRoot();
		this.initializeOptions(options);
		this.result = result;
	}	

	protected void initializeOptions (Set<String> options) {
		for (String str : options) {
    		URI uri = URI.createURI(str);    
			IFile file = this.root.getFile(new Path(uri.toPlatformString(true)));
			this.options.put(file, str);
		}
	}
	
	public Result getResult () {
		return this.result;
	}
	
	@Override
	public boolean close() {
		//set proper state to the result, call the return handler for the dialog caller
		RefTargetResourceLoadSelectionDialog.currentInstance = null;
		if (!(RefTargetResourceLoadSelectionDialog.this.result.openInCurrentEditor || RefTargetResourceLoadSelectionDialog.this.result.openInNewEditor)) {
			RefTargetResourceLoadSelectionDialog.this.result.isCancelled = true;
		}
		RefTargetResourceLoadSelectionDialog.this.result.dialogClosed();
		return super.close();
	}

	@Override
    protected Control createDialogArea(Composite parent) {
		this.getShell().addShellListener(new ShellListener() {			
			@Override
			public void shellIconified(ShellEvent e) {	
				//close the window, when it looses focus			
				RefTargetResourceLoadSelectionDialog.this.close();
			}			
			@Override
			public void shellDeiconified(ShellEvent e) {				
			}			
			@Override
			public void shellDeactivated(ShellEvent e) {		
				//close the window, when it looses focus		
				RefTargetResourceLoadSelectionDialog.this.close();
			}
			
			@Override
			public void shellClosed(ShellEvent e) {	
			}
			
			@Override
			public void shellActivated(ShellEvent e) {		
				RefTargetResourceLoadSelectionDialog.this.listViewer.getControl().setFocus();
			}
		});
		this.getShell().addListener(SWT.Resize,  new Listener () {
		    public void handleEvent (Event e) {
		        Rectangle rect = RefTargetResourceLoadSelectionDialog.this.getShell().getClientArea ();
		        RefTargetResourceLoadSelectionDialog.lastSize = new Point(rect.width, rect.height + 25);
		      }
		    });
        Composite container = (Composite) super.createDialogArea(parent);
        
        explanationText = new Text(container, SWT.READ_ONLY | SWT.WRAP | SWT.MULTI);
        explanationText.setEditable(false);
        explanationText.setText("Currently, the selected reference target cannot be resolved, but has been seen at last in the following Resources:");
        explanationText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        
        listViewer = new TableViewer (container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        listViewer.getControl().addKeyListener(new KeyListener() {			
			@Override
			public void keyReleased(KeyEvent e) {				
			}			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.ESC) {
					RefTargetResourceLoadSelectionDialog.this.close();
				} else if (e.keyCode == SWT.TRAVERSE_RETURN )  {
	        		RefTargetResourceLoadSelectionDialog.this.result.openInNewEditor = true;
	        		RefTargetResourceLoadSelectionDialog.this.close();							
				}
			}
		});
        listViewer.addSelectionChangedListener(new ISelectionChangedListener () {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getStructuredSelection().toList().size() <= 1) {
					RefTargetResourceLoadSelectionDialog.this.selectionChanged((IFile)event.getStructuredSelection().getFirstElement());
				}
			}        	
        });
        listViewer.addDoubleClickListener(new IDoubleClickListener () {
			@Override
			public void doubleClick(DoubleClickEvent event) {
        		RefTargetResourceLoadSelectionDialog.this.result.openInNewEditor = true;
        		RefTargetResourceLoadSelectionDialog.this.close();				
			}        	
        });
        
        tableView = listViewer.getTable();
        GridData gd_tableView = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        gd_tableView.heightHint = 123;
        tableView.setLayoutData(gd_tableView);
        
        listViewer.setContentProvider(new DialogContentProvider());
        listViewer.setLabelProvider(new DelegatingStyledCellLabelProvider(new DialogLabelProvider()));
        listViewer.setInput(this.options.keySet());
                
        Composite composite = new Composite(container, SWT.NONE);
        composite.setLayout(new GridLayout(3, false));
        GridData gd_composite = new GridData(SWT.RIGHT, SWT.BOTTOM, true, false, 1, 1);
        gd_composite.heightHint = 64;
        composite.setLayoutData(gd_composite);
        
        removeExplanationText = new Text(composite, SWT.READ_ONLY | SWT.WRAP | SWT.MULTI);
        removeExplanationText.setText("The Resource has been moved or deleted,");
        removeExplanationText.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		removeExplanationText.setVisible(false);
        
        Composite defaultComposite = new Composite(composite, SWT.NONE);
        defaultComposite.setLayout(null);
        GridData gd_defaultComposite = new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1);
        gd_defaultComposite.heightHint = 39;
        gd_defaultComposite.widthHint = 410;
        defaultComposite.setLayoutData(gd_defaultComposite);
        
        openComposite = new Composite(defaultComposite, SWT.NONE);
        openComposite.setBounds(0, 0, 400, 39);
        
        openInNewEditor = new Button(openComposite, SWT.CENTER);
        openInNewEditor.setSize(190, 35);
        openInNewEditor.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
        openInNewEditor.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		RefTargetResourceLoadSelectionDialog.this.result.openInNewEditor = true;
        		RefTargetResourceLoadSelectionDialog.this.close();        		
        	}
        });
        openInNewEditor.setText("Open in new Editor");
        
        openInCurrentEditor = new Button(openComposite, SWT.CENTER);
        openInCurrentEditor.setLocation(200, 0);
        openInCurrentEditor.setSize(190, 35);
        openInCurrentEditor.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
        openInCurrentEditor.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		RefTargetResourceLoadSelectionDialog.this.result.openInCurrentEditor = true;
        		RefTargetResourceLoadSelectionDialog.this.close();        		
        	}
        });
        openInCurrentEditor.setText("Open in Current Editor");
        
        removeComposite = new Composite(defaultComposite, SWT.NONE);
        removeComposite.setLayout(null);
        removeComposite.setBounds(0, 0, 400, 39);
		removeComposite.setVisible(false);
        
        removeButton = new Button(removeComposite, SWT.CENTER);
        removeButton.setText("Remove from Cache");
        removeButton.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
        removeButton.setBounds(199, 0, 190, 35);
        removeButton.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		IFile f = RefTargetResourceLoadSelectionDialog.this.currentSelection;
    			if (!f.exists()) {
    				RefTargetResourceLoadSelectionDialog.this.result.resourcesToRemove.add(RefTargetResourceLoadSelectionDialog.this.options.get(f));
    				RefTargetResourceLoadSelectionDialog.this.options.remove(f);
    			}
    			if (RefTargetResourceLoadSelectionDialog.this.options.size() <= 0) {
    				RefTargetResourceLoadSelectionDialog.this.cancelButton.notifyListeners(SWT.Selection, null);
    			} else {
    				RefTargetResourceLoadSelectionDialog.this.listViewer.refresh();      				
    			}    		
        	}
        });
        
        removeAllButton = new Button(removeComposite, SWT.CENTER);
        removeAllButton.setText("Remove All from Cache");
        removeAllButton.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
        removeAllButton.setBounds(0, 0, 190, 35);

        removeAllButton.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		new HashSet<>(RefTargetResourceLoadSelectionDialog.this.options.keySet()).forEach(f -> {
        			if (!f.exists()) {
        				RefTargetResourceLoadSelectionDialog.this.result.resourcesToRemove.add(RefTargetResourceLoadSelectionDialog.this.options.get(f));
        				RefTargetResourceLoadSelectionDialog.this.options.remove(f);
        			}
        		});
    			if (RefTargetResourceLoadSelectionDialog.this.options.size() <= 0) {
    				RefTargetResourceLoadSelectionDialog.this.cancelButton.notifyListeners(SWT.Selection, null);
    			} else {
    				RefTargetResourceLoadSelectionDialog.this.listViewer.refresh();     				
    			}       		
        	}
        });
        
        cancelButton = new Button(composite, SWT.CENTER);
        GridData gd_cancelButton = new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1);
        gd_cancelButton.widthHint = 89;
        cancelButton.setLayoutData(gd_cancelButton);
        cancelButton.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
        cancelButton.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		RefTargetResourceLoadSelectionDialog.this.result.isCancelled = true;
        		RefTargetResourceLoadSelectionDialog.this.close();        		
        	}
        });
        cancelButton.setText("Cancel");
        

        listViewer.setSelection(null);
        return container;
    }

    protected void selectionChanged(IFile firstElement) {
    	this.currentSelection = firstElement;
    	if (firstElement != null) {
    		this.result.selectedResource = this.options.get(firstElement);
    		if (firstElement.exists()) {
				this.removeComposite.setVisible(false);
				removeExplanationText.setVisible(false);
				this.openComposite.setVisible(true);
				this.openInCurrentEditor.setVisible("aas".equals(firstElement.getFullPath().getFileExtension().toLowerCase()));
			} else {
				this.removeComposite.setVisible(true);
				removeExplanationText.setVisible(true);	
				this.openComposite.setVisible(false);		
			}	
    	} else {
    		this.result.selectedResource = null;
			this.removeComposite.setVisible(false);
			removeExplanationText.setVisible(false);
			this.openComposite.setVisible(false);	    		
    	}
			
	}

	@Override
    protected Point getInitialSize() {
    	if (RefTargetResourceLoadSelectionDialog.lastSize == null) {
    		return new Point(802, 460);
    	}
    	Point p = new Point(RefTargetResourceLoadSelectionDialog.lastSize.x + 25,RefTargetResourceLoadSelectionDialog.lastSize.y + 31);
    	return p;
    }
        
    @Override
    protected Control createButtonBar(Composite parent) {
    	return null;
    }
    
    protected class DialogContentProvider extends ArrayContentProvider {
    	
    }
    
    protected class DialogLabelProvider extends LabelProvider implements ILabelProvider, IStyledLabelProvider{
   
    	protected WorkbenchLabelProvider workbenchLabelProvider;

		public DialogLabelProvider ( ) {
    		this.workbenchLabelProvider = new WorkbenchLabelProvider();
    	}
    	
    	@Override
    	public Image getImage(Object element) {	
    		try {
    			Image img = this.workbenchLabelProvider.getImage((IFile)element);  
    			if (img != null) {
    				return img;
    			}
    		} catch (Exception e) {    			
    		}
			return ExtendedImageRegistry.INSTANCE.getImage(getResourceLocator().getImage("full/obj16/file_obj"));
    	}
    	
    	@Override
    	public String getText(Object element) {
			return this.getStyledText(element).toString();
    	}
    	
    	public ResourceLocator getResourceLocator() {
    		return EMFStorageUIPlugin.getPlugin();
    	}

		@Override
		public StyledString getStyledText(Object element) {
    		IFile file = (IFile)element;
    		String path = file.getFullPath().toString();
    		if (file.exists()) {
    			return new StyledString(path);
    		}
    		return new StyledString(path, StyledString.QUALIFIER_STYLER);
		}
    }
}
