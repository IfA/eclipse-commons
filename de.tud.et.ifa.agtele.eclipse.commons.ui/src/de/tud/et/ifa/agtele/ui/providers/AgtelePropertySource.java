package de.tud.et.ifa.agtele.ui.providers;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.common.CommonPlugin;
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
import org.eclipse.emf.edit.ui.celleditor.FeatureEditorDialog;
import org.eclipse.emf.edit.ui.provider.PropertyDescriptor;
import org.eclipse.emf.edit.ui.provider.PropertySource;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
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

		protected Button addButton;
		protected Composite choiceComposite;
		protected Composite buttonComposite;
		protected Composite dialogAreaComposite;
		protected Text choiceText;
		protected Table choiceTable;

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
	
	public static class StringPositionPair implements Comparable<StringPositionPair> {
		protected Comparator<String> comparator = CommonPlugin.INSTANCE.getComparator();

		public String key;

		public int position;

		StringPositionPair(String key, int position) {
			this.key = key;
			this.position = position;
		}

		public int compareTo(StringPositionPair object) {
			if (object == this) {
				return 0;
			} else {
				StringPositionPair that = object;
				return comparator.compare(key, that.key);
			}
		}
	}
	
	//Extending this combobox is not really possible, again, all sub elements, like text and dropdown are inaccessible properties, creating a custom cell editor may be needed
	public static class AgteleExtendedComboBoxCellEditor extends ExtendedComboBoxCellEditor {

		public AgteleExtendedComboBoxCellEditor(Composite composite, ArrayList<? extends Object> list,
				ILabelProvider labelProvider, boolean sortChoices, int style, ValueHandler valueHandler,
				boolean autoShowDropDownList) {
			super(composite, list, labelProvider, sortChoices, style + DROP_DOWN_ON_KEY_ACTIVATION, valueHandler, autoShowDropDownList);

			final CCombo combo = (CCombo) getControl();

			for (Listener listener : combo.getListeners(SWT.Modify)) {
				combo.removeListener(SWT.Modify,listener);
			}
			
			combo.addKeyListener(new KeyListener () {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.character == ' ' && (e.stateMask & SWT.CTRL) > 0) {
						e.doit = false;
						combo.setListVisible(true); //
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {
					//do nothing
				}
				
			});
			
			combo.addModifyListener(new ModifyListener() {
				boolean updating;

				public void modifyText(ModifyEvent e) {
					if (!updating) {
						updating = true;

						String text = combo.getText();
						ArrayList<Object> newList = new ArrayList<Object>(originalList);
						Object valueToSelect = null;

						try {
							valueToSelect = valueHandler.toValue(text);
							if (!newList.contains(valueToSelect)) {
								newList.add(0, valueToSelect);
							}
						} catch (RuntimeException exception) {
							// Ignore.
						}

						String[] items = null;
						
						if (valueToSelect != null && originalList.contains(valueToSelect)) {
							items = AgteleExtendedComboBoxCellEditor.this.createFilteredItems(newList,
								AgteleExtendedComboBoxCellEditor.this.labelProvider, null,
								AgteleExtendedComboBoxCellEditor.this.sorted);
						} else {
							items = AgteleExtendedComboBoxCellEditor.this.createFilteredItems(newList,
								AgteleExtendedComboBoxCellEditor.this.labelProvider, text,
								AgteleExtendedComboBoxCellEditor.this.sorted);
						}
						
						AgteleExtendedComboBoxCellEditor.this.list = newList;
						combo.setItems(items);
						
						combo.notifyListeners(SWT.Selection, new Event());
						combo.setRedraw(false);				
						
						
						Point selection = combo.getSelection();
						if (AgteleExtendedComboBoxCellEditor.this.list.contains(valueToSelect)) {
							setValue(valueToSelect);
						} else if (!AgteleExtendedComboBoxCellEditor.this.list.isEmpty()) {
							setValue(AgteleExtendedComboBoxCellEditor.this.list.get(0));
						}
						combo.setText(text);
						combo.setSelection(selection);
						String oldErrorMessage = getErrorMessage();
						String newErrorMessage = valueHandler.isValid(text);
						setErrorMessage(
								newErrorMessage == null ? null : MessageFormat.format(newErrorMessage, new Object[0]));
						fireEditorValueChanged(oldErrorMessage == null, newErrorMessage == null);
						combo.setRedraw(true);
						updating = false;
					}
				}
			});
		}
		
		@Override
		protected void refreshItems(String filter) {
		    CCombo combo = (CCombo)getControl();
		    if (combo != null && !combo.isDisposed()) {
		    	ArrayList<Object> newList = new ArrayList<Object>(originalList);
		    	String[] items = this.createFilteredItems(newList, labelProvider, filter, sorted);
		    	if (!newList.equals(list)) {
		    		Object previousValue = getValue();
		    		list = newList;
		    		combo.setItems(items);
		    		if (list.contains(previousValue)) {
		    			setValue(previousValue);
		    		} else if (!list.isEmpty()) {
		    			setValue(list.get(0));
		    		}
		    	}
	    	}
		}

		public <T> String[] createFilteredItems(List<T> list, ILabelProvider labelProvider, String filter,
				boolean sorted) {
			String[] result;

			if (filter != null && filter.length() > 0) {
				sorted = true;
			}

			// If there are objects to populate...
			//
			if (list != null && list.size() > 0) {
				if (sorted) {
					List<T> unsortedList = new ArrayList<T>(list.size());
					if (filter != null && filter.length() > 0) {
						for (int i = 0; i < list.size(); i++) {
							if (this.filter(filter, labelProvider.getText(list.get(i)))) {
								unsortedList.add(list.get(i));
							}
						}
					} else {
						unsortedList.addAll(list);
					}
					list.clear();

					StringPositionPair[] pairs = new StringPositionPair[unsortedList.size()];

					for (int i = 0, size = unsortedList.size(); i < size; ++i) {
						Object object = unsortedList.get(i);
						pairs[i] = new StringPositionPair(labelProvider.getText(object), i);
					}

					Arrays.sort(pairs);

					// Create a new array.
					//
					result = new String[unsortedList.size()];
					// Fill in the result array with labels and re-populate the original list in
					// order.
					//
					for (int i = 0, size = unsortedList.size(); i < size; ++i) {
						result[i] = pairs[i].key;
						list.add(unsortedList.get(pairs[i].position));
					}
				} else {
					// Create a new array.
					//
					result = new String[list.size()];
					// Fill in the array with labels.
					//
					for (int i = 0, size = list.size(); i < size; ++i) {
						Object object = list.get(i);
						result[i] = labelProvider.getText(object);
					}
				}
			} else {
				result = new String[] { "" };
			}

			return result;
		}
		
		/**
		 * Adapted select method in order to enable a better filtering for the remaining drop down items.
		 * @param filter
		 * @param labelValue
		 * @return
		 */
		public boolean filter(String filter, String labelValue) {
			if (filter != null && filter.length() > 0) {
				if (filter.length() > labelValue.length()) {
					return false;
				}
				labelValue = labelValue.toLowerCase();
				filter = filter.toLowerCase();
				
				for (String fragment : filter.split("\\s")) {
					if (!labelValue.contains(fragment)) {
						return false;
					}
				}
			}
			return true;
		}
	}
	
//	public static class ExtendedCCombo extends CCombo {
//
//		public ExtendedCCombo(Composite parent, int style) {
//			super(parent, style);
//		}
//		
//	}
	
	public static class AgtelePropertyDescriptor extends PropertyDescriptor {
		public AgtelePropertyDescriptor(Object object, IItemPropertyDescriptor itemPropertyDescriptor) {
			super(object, itemPropertyDescriptor);
		}
		
		//TODO extend the extended cell editor in order to filter the choice of values when arbitrary values are enabled.
		public ExtendedComboBoxCellEditor createComboBoxEditor (Composite composite, Collection<? extends Object> choices, ILabelProvider labelProvider, boolean sortChoices, int style, ExtendedComboBoxCellEditor.ValueHandler handler, boolean autoShowDropDownList) {
			return new AgteleExtendedComboBoxCellEditor(composite, new ArrayList<>(choices), labelProvider, sortChoices, style, handler, autoShowDropDownList);
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
		            result = createComboBoxEditor(composite,
			                 values,
			                 getEditLabelProvider(),
			                 itemPropertyDescriptor.isSortChoices(object),
			                 SWT.NONE,
			                 new EDataTypeValueHandler((EDataType)eType, ((IItemPropertyDescriptor.ValueHandlerProvider)itemPropertyDescriptor).getValueHandler(object)),
			                 true
			            );
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
