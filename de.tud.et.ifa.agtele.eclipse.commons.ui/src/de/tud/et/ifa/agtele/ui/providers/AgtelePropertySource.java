package de.tud.et.ifa.agtele.ui.providers;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor.ValueHandler;
import org.eclipse.emf.common.ui.celleditor.ExtendedDialogCellEditor;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor.PropertyValueWrapper;
import org.eclipse.emf.edit.ui.celleditor.FeatureEditorDialog;
import org.eclipse.emf.edit.ui.provider.PropertyDescriptor;
import org.eclipse.emf.edit.ui.provider.PropertySource;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public class AgtelePropertySource extends PropertySource {

	public AgtelePropertySource(Object object, IItemPropertySource itemPropertySource) {
		super(object, itemPropertySource);
	}
	
	@Override
	protected IPropertyDescriptor createPropertyDescriptor(IItemPropertyDescriptor itemPropertyDescriptor) {
		return new AgtelePropertyDescriptor(this.object, itemPropertyDescriptor);
	}
	
	/**
	 * Extending the {@link FeatureEditorDialog} in order to add a ctrl+enter feature to properly close the dialog instead of adding content to the result list.
	 * @author Baron
	 */
	public static class ExtendedFeatureEditorDialog extends FeatureEditorDialog {

		private Button addButton;
		private Composite choiceComposite;
		private Composite buttonComposite;
		private Composite dialogAreaComposite;
		private Text choiceText;
		private Table choiceTable;

		public ExtendedFeatureEditorDialog(Shell parent, ILabelProvider labelProvider, Object object,
				EClassifier eClassifier, List<?> currentValues, String displayName, List<?> choiceOfValues,
				boolean multiLine, boolean sortChoices, boolean unique, ValueHandler valueHandler) {
			super(parent, labelProvider, object, eClassifier, currentValues, displayName, choiceOfValues, multiLine, sortChoices,
					unique, valueHandler);
		}
		
		@Override
		protected Control createContents(Composite parent) {
			Control result = super.createContents(parent);			
			return result;
		}
		
		@Override
		protected Control createDialogArea(Composite parent) {
			dialogAreaComposite = (Composite) super.createDialogArea(parent);
			
			choiceComposite = (Composite) dialogAreaComposite.getChildren()[choiceOfValues != null ? 1 : 0];
			buttonComposite = (Composite) dialogAreaComposite.getChildren()[choiceOfValues != null ? 2 : 1]; 
			
			addButton = (Button) buttonComposite.getChildren()[1];
								
			if (choiceComposite.getChildren()[1] instanceof Text) {
				choiceText = (Text) choiceComposite.getChildren()[1];
				
				for (Listener l : choiceText.getListeners(SWT.KeyDown)) {
					choiceText.removeListener(SWT.KeyDown, l);
					choiceText.removeListener(SWT.KeyUp, l);
				}
				choiceText.addKeyListener(new KeyAdapter() {
			          @Override
			          public void keyPressed(KeyEvent event) {
			        	  if (event.character == '\r' || event.character == '\n') {
			        		  if ((event.stateMask & SWT.CTRL) != 0) {
			                      event.doit = false;
			        			  ExtendedFeatureEditorDialog.this.getButton(OK).notifyListeners(SWT.Selection, null);
			        		  } else if (!multiLine) {						            		
					              event.doit = false;
					              if (addButton.isEnabled()) {
					            	  addButton.notifyListeners(SWT.Selection, null);
					              }
			        		  }
			        	  }
			          }
		        });
			} else if (choiceComposite.getChildren()[1] instanceof Table) {
				choiceTable = (Table) choiceComposite.getChildren()[1];
				//hooking the choice table is far more complicated since the table viewers are method variables and not accessible without copying the whole class
			}
			
			return dialogAreaComposite;
		}
	}
	
	public static class AgtelePropertyDescriptor extends PropertyDescriptor {
		public AgtelePropertyDescriptor(Object object, IItemPropertyDescriptor itemPropertyDescriptor) {
			super(object, itemPropertyDescriptor);
		}
		
		//TODO extend the extended cell editor in order to filter the choice of values when arbitrary values are enabled.
		public ExtendedComboBoxCellEditor createComboBoxEditor (Composite composite, Collection<? extends Object> choices, ILabelProvider labelProvider, boolean sortChoices, int style, ExtendedComboBoxCellEditor.ValueHandler handler, boolean autoShowDropDownList) {
			return new ExtendedComboBoxCellEditor(composite, new ArrayList<>(choices), labelProvider, sortChoices, style, handler, autoShowDropDownList);
		}
		
		public FeatureEditorDialog createFeatureEditorDialog(Control control, ILabelProvider labelProvider, EStructuralFeature feature, List<?> value, Collection<? extends Object> choices, boolean multiline, boolean sort, ValueHandler handler) {
			return new ExtendedFeatureEditorDialog(
                    control.getShell(),
                    labelProvider,
                    object,
                    feature.getEType(),
                    value,
                    getDisplayName(),
                    choices != null ? new ArrayList<Object>(choices) : null,
                    multiline,
                    sort,
                    feature.isUnique() || feature instanceof EReference,
                    handler
                  );
		}
		
		public ExtendedDialogCellEditor createDialogCellEditor (Composite composite, ILabelProvider labelProvider, EStructuralFeature feature, Collection<? extends Object> choices, boolean multiline, boolean sort, ValueHandler handler) {
			return new ExtendedDialogCellEditor(composite, labelProvider) {
				@Override
                protected Object openDialogBox(Control cellEditorWindow) {
                  FeatureEditorDialog dialog = createFeatureEditorDialog(cellEditorWindow, labelProvider, feature, (List<?>)doGetValue(), choices, multiline, sort, handler);
                  dialog.open();
                  return dialog.getResult();
                }
            };
		}
		
		@Override
		public CellEditor createPropertyEditor(Composite composite) {
		    CellEditor result = createPropertyEditorFromFactory(composite);
		    if (result != null)
		    {
		      return result;
		    }

		    if (!itemPropertyDescriptor.canSetProperty(object))
		    {
		      return null;
		    }

		    Object genericFeature = itemPropertyDescriptor.getFeature(object);
		    if (genericFeature instanceof EReference[])
		    {
		      result = createComboBoxEditor(
		        composite,
		        itemPropertyDescriptor.getChoiceOfValues(object),
		        getEditLabelProvider(),
		        itemPropertyDescriptor.isSortChoices(object),
		        SWT.READ_ONLY,
		        null,
		        true);
		    }
		    else if (genericFeature instanceof EStructuralFeature)
		    {
		      final EStructuralFeature feature = (EStructuralFeature)genericFeature;
		      final EClassifier eType = feature.getEType();
		      final Collection<?> choiceOfValues = itemPropertyDescriptor.getChoiceOfValues(object);
		      if (choiceOfValues != null)
		      {
		        if (itemPropertyDescriptor.isMany(object))
		        {
		          boolean valid = true;
		          for (Object choice : choiceOfValues)
		          {
		            if (!eType.isInstance(choice))
		            {
		              valid = false;
		              break;
		            }
		          }

		          if (valid)
		          {
		            final ILabelProvider editLabelProvider = getEditLabelProvider();
		            result = createDialogCellEditor(composite, editLabelProvider, feature, choiceOfValues, false, itemPropertyDescriptor.isSortChoices(object), 
		            		itemPropertyDescriptor instanceof IItemPropertyDescriptor.ValueHandlerProvider &&
		                      ((IItemPropertyDescriptor.ValueHandlerProvider)itemPropertyDescriptor).isChoiceArbitrary(object) ?
		                              ((IItemPropertyDescriptor.ValueHandlerProvider)itemPropertyDescriptor).getValueHandler(object) :
		                              null
		               );     
		          }
		        }

		        if (result == null)
		        {
		          ArrayList<Object> values = new ArrayList<Object>(choiceOfValues);
		          if (itemPropertyDescriptor instanceof IItemPropertyDescriptor.ValueHandlerProvider &&
		                ((IItemPropertyDescriptor.ValueHandlerProvider)itemPropertyDescriptor).isChoiceArbitrary(object))
		          {
		        	  ItemPropertyDescriptor.PropertyValueWrapper currentValue = (PropertyValueWrapper) itemPropertyDescriptor.getPropertyValue(object);
		        	  if (currentValue != null && currentValue.getEditableValue(object) != null) {
		        		  values.add(0, currentValue.getEditableValue(object));		        		  
		        	  }
		            result = createComboBoxEditor(composite,
			                 values,
			                 getEditLabelProvider(),
			                 itemPropertyDescriptor.isSortChoices(object),
			                 SWT.NONE,
			                 new EDataTypeValueHandler((EDataType)eType, ((IItemPropertyDescriptor.ValueHandlerProvider)itemPropertyDescriptor).getValueHandler(object)),
			                 false
			            );
//		            ((ExtendedComboBoxCellEditor)result);
		          }
		          else
		          {
		            result = createComboBoxEditor(composite, values, 
		            		  getEditLabelProvider(), itemPropertyDescriptor.isSortChoices(object), SWT.READ_ONLY, null, true);
		          }
		        }
		      }
		      else if (eType instanceof EDataType)
		      {
		        final EDataType eDataType = (EDataType)eType;
		        if (eDataType.isSerializable())
		        {
		          if (itemPropertyDescriptor.isMany(object))
		          {
		            final ILabelProvider editLabelProvider = getEditLabelProvider();
		            result =
		            	createDialogCellEditor(composite, editLabelProvider, feature, null, itemPropertyDescriptor.isMultiLine(object), false,
	            			itemPropertyDescriptor instanceof IItemPropertyDescriptor.ValueHandlerProvider ?
	      	                      ((IItemPropertyDescriptor.ValueHandlerProvider)itemPropertyDescriptor).getValueHandler(object) :
	      	                      null
	            		);
		          }
		          else if (eDataType.getInstanceClass() == Boolean.class) 
		          {
		            return new CheckBoxCellEditor(composite, SWT.NONE, true);
		          }
		          else if (eDataType.getInstanceClass() == Boolean.TYPE)
		          {
		            return new CheckBoxCellEditor(composite, SWT.NONE, false);
		          }
		          else if (itemPropertyDescriptor instanceof IItemPropertyDescriptor.ValueHandlerProvider)
		          {
		            result = createEDataTypeCellEditor(eDataType, ((IItemPropertyDescriptor.ValueHandlerProvider)itemPropertyDescriptor).getValueHandler(object), composite);
		          }
		          else
		          {
		            result = createEDataTypeCellEditor(eDataType, composite);
		          }
		        }
		      }
		    }

		    return result;
		}
	}
	
	/**
	 * A delegate for handling validation and conversion for data type values.
	 */
	public static class EDataTypeValueHandler implements ICellEditorValidator, IInputValidator, ExtendedComboBoxCellEditor.ValueHandler {
		protected EDataType eDataType;
	    /**
		 * @since 2.14
		 */
		protected IItemPropertyDescriptor.ValueHandler valueHandler;
		
		public EDataTypeValueHandler(EDataType eDataType) {
		    this(eDataType, new ItemPropertyDescriptor.DataTypeValueHandler(eDataType));
		}
		
		/**
		 * @since 2.14
		 */
		public EDataTypeValueHandler(EDataType eDataType, IItemPropertyDescriptor.ValueHandler valueHandler) {
			this.eDataType = eDataType;
			this.valueHandler = valueHandler;
		}
		
		public String isValid(Object object) {
			return escape(valueHandler.isValid((String)object));
		}
		
		/**
		 * JFace {@link MessageFormat#format(String, Object...) formats} messages, so special pattern characters need to be escaped.
		 * 
		 * @since 2.14
		 */
		protected String escape(String message) {
			return message == null ? null : message.replaceAll("'","''").replaceAll("\\{", "'{'"); // }}
		}
		
		public String isValid(String text) {
			return isValid((Object)text);
		}
		
		public Object toValue(String string) {
			return valueHandler.toValue(string);
		}
		
		public String toString(Object value) {
			  String result = valueHandler.toString(value);
			  return result == null ? "" : result;
		}
	}
}
