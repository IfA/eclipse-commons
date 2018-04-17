# AG Tele Eclipse Commons 

A set of utility classes related to *Eclipse* and the [Eclipse Modeling Framework](https://www.eclipse.org/modeling/emf/) developed at the [Institute of Automation at TU Dresden](http://www.et.tu-dresden.de/ifa/).

## Key Features

* An [enhanced, reusable tree viewer](/de.tud.et.ifa.agtele.eclipse.commons.ui/src/de/tud/et/ifa/agtele/ui/widgets/TreeViewerGroup.java) that provides support for filtering, persisting/restoring, cloning and provides a model element palette to simplify adding new model elements,
* an enhanced version of the *Sample Ecore Model Editor*/*Sample Reflective Ecore Model Editor* ([AgteleEcoreEditor](/de.tud.et.ifa.agtele.eclipse.commons.ui/src/de/tud/et/ifa/agtele/ui/editors/AgteleEcoreEditor.java)) that is based on this reusable treeviewer,
* a [view](/de.tud.et.ifa.agtele.eclipse.commons.ui/src/de/tud/et/ifa/agtele/ui/views/EMFModelHelpView.java) that is able to display documentation information incorporated into Ecore models,
* various utility classes to simplify working with [Resources](/de.tud.et.ifa.agtele.eclipse.commons/src/de/tud/et/ifa/agtele/resources/ResourceHelper.java), [Eclipse bundle content](/de.tud.et.ifa.agtele.eclipse.commons/src/de/tud/et/ifa/agtele/resources/BundleContentHelper.java), the [Eclipse UI](/de.tud.et.ifa.agtele.eclipse.commons.ui/src/de/tud/et/ifa/agtele/ui/util/UIHelper.java), [EMF in general](/de.tud.et.ifa.agtele.eclipse.commons/src/de/tud/et/ifa/agtele/emf/AgteleEcoreUtil.java), [EPackages](/de.tud.et.ifa.agtele.eclipse.commons/src/de/tud/et/ifa/agtele/emf/EPackageHelper.java) and [EMFCompare](/de.tud.et.ifa.agtele.eclipse.commons/src/de/tud/et/ifa/agtele/emf/compare/EMFCompareUtil.java)
* and much more ...

## Installation

The current version of the Eclipse Commons can be installed via the [Update Site].

Note: This update site is only available from within the campus network of TU Dresden! If you have no possibility to connect to this network, you need to clone the repository and install the feature manually.

[Update Site]: https://agtele.eats.et.tu-dresden.de/eclipse/updates/