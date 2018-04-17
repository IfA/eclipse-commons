# Modifications

The `ClonableEcoreEditor` (de.tud.et.ifa.agtele.ui.editors.ClonableEcoreEditor) in this plug-in is a fork of the `EcoreEditor` (org.eclipse.emf.ecore.editor.EcoreEditor) that is available [here](https://github.com/eclipse/emf/blob/master/plugins/org.eclipse.emf.ecore.editor/src/org/eclipse/emf/ecore/presentation/EcoreEditor.java).

The following modifications have been made to this file (modifications are marked/explained in detail in the source code file):
* Rename/Move the file from `org.eclipse.emf.ecore.editor.EcoreEditor` to `de.tud.et.ifa.agtele.ui.editors.ClonableEcoreEditor`
* Extend the `de.tud.et.ifa.agtele.ui.editors.ClonableEditor` to make the editor 'clonable' and introduce the necessary changes to the existing code