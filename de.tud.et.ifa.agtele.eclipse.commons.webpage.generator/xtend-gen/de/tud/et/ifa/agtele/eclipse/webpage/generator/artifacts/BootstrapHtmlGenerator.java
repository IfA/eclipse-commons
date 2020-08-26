package de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts;

import de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts.BasicHtmlGenerator;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public interface BootstrapHtmlGenerator extends BasicHtmlGenerator {
  @Override
  default List<String> linkedStylesheets() {
    final List<String> list = BasicHtmlGenerator.super.linkedStylesheets();
    String _baseUrl = this.getWebPage().getBaseUrl();
    String _plus = (_baseUrl + "/templateResources/css/bootstrap.min.css");
    list.add(_plus);
    String _baseUrl_1 = this.getWebPage().getBaseUrl();
    String _plus_1 = (_baseUrl_1 + "/templateResources/css/style.css");
    list.add(_plus_1);
    return list;
  }
  
  @Override
  default List<String> linkedScripts() {
    final List<String> list = BasicHtmlGenerator.super.linkedScripts();
    String _baseUrl = this.getWebPage().getBaseUrl();
    String _plus = (_baseUrl + "/templateResources/js/jquery-3.3.1.slim.min.js");
    list.add(_plus);
    String _baseUrl_1 = this.getWebPage().getBaseUrl();
    String _plus_1 = (_baseUrl_1 + "/templateResources/js/bootstrap.bundle.min.js");
    list.add(_plus_1);
    String _baseUrl_2 = this.getWebPage().getBaseUrl();
    String _plus_2 = (_baseUrl_2 + "/templateResources/js/application.js");
    list.add(_plus_2);
    return list;
  }
  
  default String rootNavBarContent() {
    StringConcatenation _builder = new StringConcatenation();
    return _builder.toString();
  }
  
  default String additionalRooNavbarContent() {
    StringConcatenation _builder = new StringConcatenation();
    return _builder.toString();
  }
  
  default boolean suppressNavBar() {
    return false;
  }
  
  default String getLangSwitchLinks() {
    StringConcatenation _builder = new StringConcatenation();
    return _builder.toString();
  }
  
  @Override
  default String pageTitle() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<div class=\"row\">");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<div class=\"col\">");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<h1>");
    String _title = this.title();
    _builder.append(_title, "\t\t");
    _builder.append("</h1>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("</div>");
    _builder.newLine();
    _builder.append("</div>");
    _builder.newLine();
    return _builder.toString();
  }
  
  default String getPageTitleText() {
    StringConcatenation _builder = new StringConcatenation();
    return _builder.toString();
  }
  
  @Override
  default String header() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<div class=\"container bg-tu p-3\">");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<div class=\"row\">");
    _builder.newLine();
    _builder.append("\t\t");
    String _valueContent = this.getValueContent(this.getHtmlFragment().getTargetHeader());
    _builder.append(_valueContent, "\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("</div>");
    _builder.newLine();
    _builder.append("</div>");
    _builder.newLine();
    {
      boolean _suppressNavBar = this.suppressNavBar();
      boolean _not = (!_suppressNavBar);
      if (_not) {
        _builder.append("<div class=\"container navcontainer\">");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("<nav class=\"navbar navbar-expand-sm navbar-dark bg-tu\">");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarNav\" aria-controls=\"navbarNav\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<span class=\"navbar-toggler-icon\"></span>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("</button>");
        _builder.newLine();
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<div class=\"collapse navbar-collapse\" id=\"navbarNav\">");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("<ul class=\"navbar-nav\">");
        _builder.newLine();
        _builder.append("\t\t\t\t");
        _builder.append("<!-- Links -->");
        _builder.newLine();
        _builder.append("\t\t\t\t");
        String _rootNavBarContent = this.rootNavBarContent();
        _builder.append(_rootNavBarContent, "\t\t\t\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.newLine();
        _builder.append("\t\t\t\t\t\t\t\t\t\t\t\t\t");
        _builder.append("</ul>");
        _builder.newLine();
        _builder.append("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
        _builder.append("</div> ");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("</nav>");
        _builder.newLine();
        _builder.append("\t");
        String _additionalRooNavbarContent = this.additionalRooNavbarContent();
        _builder.append(_additionalRooNavbarContent, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("</div>");
        _builder.newLine();
      }
    }
    return _builder.toString();
  }
  
  @Override
  default String footer() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<footer>");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("<div class=\"container bg-tu p-3\">");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<div class=\"row\">");
    _builder.newLine();
    _builder.append("\t\t\t");
    String _valueContent = this.getValueContent(this.getHtmlFragment().getTargetFooter());
    _builder.append(_valueContent, "\t\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("</div>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</div>");
    _builder.newLine();
    _builder.append("</footer>");
    _builder.newLine();
    return _builder.toString();
  }
  
  @Override
  default boolean useNavigation() {
    return false;
  }
  
  default String getPageTitle() {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isBlank = this.getPageTitleText().isBlank();
      boolean _not = (!_isBlank);
      if (_not) {
        _builder.append("<div class=\"row ");
        {
          boolean _useNavigation = this.useNavigation();
          if (_useNavigation) {
            _builder.append("justify-content-end");
          }
        }
        _builder.append("\">");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<div class=\"col");
        {
          boolean _useNavigation_1 = this.useNavigation();
          if (_useNavigation_1) {
            _builder.append("-11");
          }
        }
        _builder.append("\">\t\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("<h1>");
        String _pageTitleText = this.getPageTitleText();
        _builder.append(_pageTitleText, "\t\t");
        _builder.append("</h1>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("</div>");
        _builder.newLine();
        _builder.append("</div>");
        _builder.newLine();
      }
    }
    return _builder.toString();
  }
  
  @Override
  default String mainStructure() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<div class=\"container\">");
    _builder.newLine();
    _builder.append("\t");
    String _beforeTitle = this.beforeTitle();
    _builder.append(_beforeTitle, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    String _pageTitle = this.getPageTitle();
    _builder.append(_pageTitle, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    String _belowTitle = this.belowTitle();
    _builder.append(_belowTitle, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<div class=\"row section-padding\">");
    _builder.newLine();
    {
      boolean _useNavigation = this.useNavigation();
      if (_useNavigation) {
        _builder.append("\t\t");
        String _navigation = this.navigation();
        _builder.append(_navigation, "\t\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    String _mainContent = this.mainContent();
    _builder.append(_mainContent, "\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("</div>");
    _builder.newLine();
    _builder.append("</div>");
    _builder.newLine();
    return _builder.toString();
  }
  
  default String belowTitle() {
    return "";
  }
  
  default String beforeTitle() {
    return "";
  }
}
