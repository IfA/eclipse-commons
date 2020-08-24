package de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts;

import de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts.IArtifactGenerator;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Announcement;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Base;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public interface BasicHtmlGenerator extends IArtifactGenerator {
  default String title() {
    return "NO_NAME";
  }
  
  default List<String> linkedStylesheets() {
    ArrayList<String> result = new ArrayList<String>();
    result.addAll(
      this.getValueContents(
        this.getHtmlFragment().expandIncludes(this.getHtmlFragment().styleList(), false)));
    return result;
  }
  
  default List<String> linkedScripts() {
    ArrayList<String> result = new ArrayList<String>();
    result.addAll(
      this.getValueContents(
        this.getHtmlFragment().expandIncludes(this.getHtmlFragment().scriptList(), false)));
    return result;
  }
  
  default List<String> inlineStylesheets() {
    ArrayList<String> result = new ArrayList<String>();
    result.addAll(
      this.getValueContents(
        this.getHtmlFragment().expandIncludes(this.getHtmlFragment().styleList(), true)));
    return result;
  }
  
  default List<String> inlineScripts() {
    ArrayList<String> result = new ArrayList<String>();
    result.addAll(
      this.getValueContents(
        this.getHtmlFragment().expandIncludes(this.getHtmlFragment().scriptList(), true)));
    return result;
  }
  
  default String header() {
    StringConcatenation _builder = new StringConcatenation();
    return _builder.toString();
  }
  
  default String navigation() {
    StringConcatenation _builder = new StringConcatenation();
    return _builder.toString();
  }
  
  default String footer() {
    StringConcatenation _builder = new StringConcatenation();
    return _builder.toString();
  }
  
  default String mainContent() {
    StringConcatenation _builder = new StringConcatenation();
    return _builder.toString();
  }
  
  default String specialContent() {
    StringConcatenation _builder = new StringConcatenation();
    return _builder.toString();
  }
  
  default String pageTitle() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<h1>");
    String _title = this.title();
    _builder.append(_title);
    _builder.append("</h1>");
    return _builder.toString();
  }
  
  default String mainStructure() {
    StringConcatenation _builder = new StringConcatenation();
    String _pageTitle = this.pageTitle();
    _builder.append(_pageTitle);
    _builder.newLineIfNotEmpty();
    String _mainContent = this.mainContent();
    _builder.append(_mainContent);
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  default boolean useNavigation() {
    return true;
  }
  
  @Override
  default String getContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<!DOCTYPE html>");
    _builder.newLine();
    _builder.append("<html>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<head>\t\t\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<meta charset=\"UTF-8\"/>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<title>");
    String _title = this.title();
    _builder.append(_title, "\t\t");
    _builder.append("</title>");
    _builder.newLineIfNotEmpty();
    {
      List<String> _linkedStylesheets = this.linkedStylesheets();
      for(final String ls : _linkedStylesheets) {
        {
          if (((ls != null) && (!ls.isBlank()))) {
            _builder.append("\t\t");
            _builder.append("<link rel=\"stylesheet\"  type=\"text/css\" href=\"");
            _builder.append(ls, "\t\t");
            _builder.append("\"/>");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      List<String> _inlineStylesheets = this.inlineStylesheets();
      for(final String inlineStyle : _inlineStylesheets) {
        {
          if (((inlineStyle != null) && (!inlineStyle.isBlank()))) {
            _builder.append("\t\t");
            _builder.append("<style>");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append(inlineStyle, "\t\t\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("</style>");
            _builder.newLine();
          }
        }
      }
    }
    {
      List<String> _linkedScripts = this.linkedScripts();
      for(final String ls_1 : _linkedScripts) {
        {
          if (((ls_1 != null) && (!ls_1.isBlank()))) {
            _builder.append("\t\t");
            _builder.append("<script src=\"");
            _builder.append(ls_1, "\t\t");
            _builder.append("\">/*no content*/</script>");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.append("</head>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<body>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<header>");
    _builder.newLine();
    _builder.append("\t\t\t");
    String _header = this.header();
    _builder.append(_header, "\t\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("</header>");
    _builder.newLine();
    {
      boolean _useNavigation = this.useNavigation();
      if (_useNavigation) {
        _builder.append("\t\t");
        _builder.append("<nav>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("\t");
        String _navigation = this.navigation();
        _builder.append(_navigation, "\t\t\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("</nav>");
        _builder.newLine();
      }
    }
    _builder.append("\t\t");
    _builder.append("<main>");
    _builder.newLine();
    _builder.append("\t\t\t");
    String _mainStructure = this.mainStructure();
    _builder.append(_mainStructure, "\t\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("</main>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<footer>");
    _builder.newLine();
    _builder.append("\t\t\t");
    String _footer = this.footer();
    _builder.append(_footer, "\t\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("</footer>");
    _builder.newLine();
    {
      List<String> _inlineScripts = this.inlineScripts();
      for(final String inlineScript : _inlineScripts) {
        {
          if (((inlineScript != null) && (!inlineScript.isBlank()))) {
            _builder.append("\t\t");
            _builder.append("<script type=\"text/javascript\">");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append(inlineScript, "\t\t\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("</script>");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.append("</body>");
    _builder.newLine();
    _builder.append("</html>\t\t");
    _builder.newLine();
    return _builder.toString();
  }
  
  default String stringToLink(final String uri, final String displayedString) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<a href=\"");
    _builder.append(uri);
    _builder.append("\">");
    _builder.append(displayedString);
    _builder.append("</a>");
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  default String printMetaData(final Base b) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((((((b.getCreatedOn() != null) && (!b.getCreatedOn().isBlank())) || ((b.getCreatedBy() != null) && (!b.getCreatedBy().isBlank()))) || ((b.getLastModified() != null) && (!b.getLastModified().isBlank()))) || ((b.getLastModifiedBy() != null) && (!b.getLastModifiedBy().isBlank())))) {
        _builder.append("<footer class=\"metadata blockquote-footer\">");
        _builder.newLine();
        {
          if ((((b.getCreatedOn() != null) && (!b.getCreatedOn().isBlank())) || ((b.getCreatedBy() != null) && (!b.getCreatedBy().isBlank())))) {
            _builder.append("Created ");
            {
              if (((b.getCreatedOn() != null) && (!b.getCreatedOn().isBlank()))) {
                _builder.append("on ");
                String _createdOn = b.getCreatedOn();
                _builder.append(_createdOn);
              }
            }
            {
              if (((b.getCreatedBy() != null) && (!b.getCreatedBy().isBlank()))) {
                _builder.append(" by ");
                String _createdBy = b.getCreatedBy();
                _builder.append(_createdBy);
              }
            }
            _builder.append(" ");
            _builder.newLineIfNotEmpty();
            {
              if ((((b.getLastModified() != null) && (!b.getLastModified().isBlank())) || ((b.getLastModifiedBy() != null) && (!b.getLastModifiedBy().isBlank())))) {
                _builder.append("; ");
              }
            }
            _builder.newLineIfNotEmpty();
          }
        }
        {
          if ((((b.getLastModified() != null) && (!b.getLastModified().isBlank())) || ((b.getLastModifiedBy() != null) && (!b.getLastModifiedBy().isBlank())))) {
            _builder.append("Last modified ");
            {
              if (((b.getLastModified() != null) && (!b.getLastModified().isBlank()))) {
                _builder.append("on ");
                String _lastModified = b.getLastModified();
                _builder.append(_lastModified);
              }
            }
            {
              if (((b.getLastModifiedBy() != null) && (!b.getLastModifiedBy().isBlank()))) {
                _builder.append(" by ");
                String _lastModifiedBy = b.getLastModifiedBy();
                _builder.append(_lastModifiedBy);
              }
            }
            _builder.append(" ");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("</footer>");
        _builder.newLine();
      }
    }
    return _builder.toString();
  }
  
  default String getAnnouncementCssClass(final Announcement a) {
    return a.getType().getLiteral().toLowerCase();
  }
  
  default String printAnnouncement(final Announcement a) {
    String content = this.getValueContent(a.getContent());
    if ((content == null)) {
      StringConcatenation _builder = new StringConcatenation();
      return _builder.toString();
    }
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("<div class=\"alert alert-primary ");
    {
      boolean _isClosable = a.isClosable();
      if (_isClosable) {
        _builder_1.append("alert-dismissible");
      }
    }
    _builder_1.append("\" role=\"alert\">");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append(" \t");
    _builder_1.append(content, " \t");
    String _printMetaData = this.printMetaData(a);
    _builder_1.append(_printMetaData, " \t");
    _builder_1.newLineIfNotEmpty();
    {
      boolean _isClosable_1 = a.isClosable();
      if (_isClosable_1) {
        _builder_1.append(" \t");
        _builder_1.append("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">");
        _builder_1.newLine();
        _builder_1.append(" \t");
        _builder_1.append("\t");
        _builder_1.append("<span aria-hidden=\"true\">&times;</span>");
        _builder_1.newLine();
        _builder_1.append(" \t");
        _builder_1.append("</button>");
        _builder_1.newLine();
      }
    }
    _builder_1.append("</div>");
    _builder_1.newLine();
    return _builder_1.toString();
  }
  
  default String printAnnouncements(final List<Announcement> announcements) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Announcement a : announcements) {
        String _printAnnouncement = this.printAnnouncement(a);
        _builder.append(_printAnnouncement);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder.toString();
  }
}
