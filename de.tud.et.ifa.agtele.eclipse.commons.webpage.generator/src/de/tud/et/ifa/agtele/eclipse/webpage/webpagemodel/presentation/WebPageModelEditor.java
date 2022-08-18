/**
 */
package de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.presentation;


import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.provider.WebPageModelItemProviderAdapterFactory;
import de.tud.et.ifa.agtele.ui.util.CommonEditingDomainViewerDropAdapter;
import de.tud.et.ifa.agtele.ui.editors.ClonableEditor;
import de.tud.et.ifa.agtele.ui.interfaces.IPersistable;
import de.tud.et.ifa.agtele.ui.util.TreeViewNavigationLocation;
import de.tud.et.ifa.agtele.ui.providers.AgteleContentProvider;
import de.tud.et.ifa.agtele.ui.widgets.TreeViewerGroup;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.provider.WebpageEditPlugin;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.INavigationLocationProvider;
import org.eclipse.emf.common.EMFPlugin;

import de.tud.et.ifa.agtele.emf.edit.AgteleStyledLabelProvider;
import de.tud.et.ifa.agtele.emf.sanitizer.ModelSanitizer;

import org.eclipse.emf.edit.ui.provider.DelegatingStyledCellLabelProvider;
import org.eclipse.emf.edit.ui.provider.DiagnosticDecorator;
