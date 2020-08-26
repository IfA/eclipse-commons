package de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts;

import com.google.common.base.Objects;
import de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts.AbstractPageGenerator;
import de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts.BootstrapHtmlGenerator;
import de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts.BootstrapTreeMenuHelper;
import de.tud.et.ifa.agtele.eclipse.webpage.util.IStringSubstitutor;
import de.tud.et.ifa.agtele.eclipse.webpage.util.ResultReporter;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AnnouncementLocationEnum;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Page;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.StringValue;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.SubPage;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Value;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class PageGenerator extends AbstractPageGenerator implements BootstrapHtmlGenerator {
  public PageGenerator(final AbstractHTML fragment, final ResultReporter reporter, final IStringSubstitutor substitutor) {
    super(fragment, reporter, substitutor);
  }
  
  @Override
  public String navigation() {
    Page _xifexpression = null;
    Page _asPage = this.asPage();
    boolean _tripleNotEquals = (_asPage != null);
    if (_tripleNotEquals) {
      _xifexpression = this.asPage();
    } else {
      _xifexpression = this.fragment.getPage();
    }
    final BootstrapTreeMenuHelper helper = new BootstrapTreeMenuHelper(_xifexpression, this.fragment);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<div class=\"col-3 border\">");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<div class=\"just-padding\">");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<h4>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<svg width=\"1em\" height=\"1em\" viewBox=\"0 0 16 16\" class=\"bi bi-diagram-3-fill\" fill=\"currentColor\" xmlns=\"http://www.w3.org/2000/svg\">");
    _builder.newLine();
    _builder.append("\t\t  \t\t");
    _builder.append("<path fill-rule=\"evenodd\" d=\"M8 5a.5.5 0 0 1 .5.5V7H14a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-1 0V8h-5v.5a.5.5 0 0 1-1 0V8h-5v.5a.5.5 0 0 1-1 0v-1A.5.5 0 0 1 2 7h5.5V5.5A.5.5 0 0 1 8 5zm-8 6.5A1.5 1.5 0 0 1 1.5 10h1A1.5 1.5 0 0 1 4 11.5v1A1.5 1.5 0 0 1 2.5 14h-1A1.5 1.5 0 0 1 0 12.5v-1zm6 0A1.5 1.5 0 0 1 7.5 10h1a1.5 1.5 0 0 1 1.5 1.5v1A1.5 1.5 0 0 1 8.5 14h-1A1.5 1.5 0 0 1 6 12.5v-1zm6 0a1.5 1.5 0 0 1 1.5-1.5h1a1.5 1.5 0 0 1 1.5 1.5v1a1.5 1.5 0 0 1-1.5 1.5h-1a1.5 1.5 0 0 1-1.5-1.5v-1z\"/>");
    _builder.newLine();
    _builder.append("\t\t  \t\t");
    _builder.append("<path fill-rule=\"evenodd\" d=\"M6 3.5A1.5 1.5 0 0 1 7.5 2h1A1.5 1.5 0 0 1 10 3.5v1A1.5 1.5 0 0 1 8.5 6h-1A1.5 1.5 0 0 1 6 4.5v-1z\"/>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("</svg>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("</h4>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<div class=\"list-group list-group-root list-group-flush\">");
    _builder.newLine();
    {
      EStructuralFeature _eContainingFeature = this.fragment.eContainingFeature();
      boolean _equals = Objects.equal(_eContainingFeature, WebPageModelPackage.Literals.SUB_PAGE__SUB_PAGE);
      if (_equals) {
        _builder.append("\t\t\t");
        _builder.append("<a class=\"btn btn-light up-link\" href=\"");
        EObject _eContainer = this.fragment.eContainer();
        String _url = ((AbstractHTML) _eContainer).getUrl();
        _builder.append(_url, "\t\t\t");
        _builder.append("\">");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t\t");
        _builder.append("\t");
        _builder.append("<span class=\"inline\">");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("\t\t");
        _builder.append("<svg class=\"bi bi-arrow-up\" width=\"1em\" height=\"1em\" viewBox=\"0 0 16 16\" fill=\"currentColor\" xmlns=\"http://www.w3.org/2000/svg\">");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("\t\t  ");
        _builder.append("<path fill-rule=\"evenodd\" d=\"M8 3.5a.5.5 0 01.5.5v9a.5.5 0 01-1 0V4a.5.5 0 01.5-.5z\" clip-rule=\"evenodd\"/>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("\t\t  ");
        _builder.append("<path fill-rule=\"evenodd\" d=\"M7.646 2.646a.5.5 0 01.708 0l3 3a.5.5 0 01-.708.708L8 3.707 5.354 6.354a.5.5 0 11-.708-.708l3-3z\" clip-rule=\"evenodd\"/>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("\t\t");
        _builder.append("</svg>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("\t");
        _builder.append("</span>");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("\t");
        _builder.append("Up to ");
        EObject _eContainer_1 = this.fragment.eContainer();
        String _name = ((AbstractHTML) _eContainer_1).getName();
        _builder.append(_name, "\t\t\t\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t\t");
        _builder.append("</a>");
        _builder.newLine();
      }
    }
    _builder.append("\t\t\t");
    String _createMenu = helper.createMenu();
    _builder.append(_createMenu, "\t\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("</div>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</div>");
    _builder.newLine();
    _builder.append("</div>");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String getIcon(final Page page) {
    boolean _hasIcon = page.hasIcon();
    if (_hasIcon) {
      String _iconUrl = page.getIconUrl();
      boolean _tripleNotEquals = (_iconUrl != null);
      if (_tripleNotEquals) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("<span style=\"width:16px;background-image:url(\'");
        String _iconUrl_1 = page.getIconUrl();
        _builder.append(_iconUrl_1);
        _builder.append("\');\"");
        _builder.newLineIfNotEmpty();
        return _builder.toString();
      } else {
        Value _navIcon = page.getNavIcon();
        if ((_navIcon instanceof StringValue)) {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("<span>");
          _builder_1.newLine();
          _builder_1.append("\t");
          Value _navIcon_1 = page.getNavIcon();
          String _value = ((StringValue) _navIcon_1).getValue();
          _builder_1.append(_value, "\t");
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("</span>");
          _builder_1.newLine();
          return _builder_1.toString();
        }
      }
    }
    StringConcatenation _builder_2 = new StringConcatenation();
    return _builder_2.toString();
  }
  
  public boolean isRootNode(final AbstractHTML element) {
    return ((element != null) && (!((element instanceof Page) && (!(element.eContainer() instanceof WebPage)))));
  }
  
  public List<Page> getMainBreadCrumbs(final AbstractHTML element) {
    List<Page> result = new ArrayList<Page>();
    Page _xifexpression = null;
    if ((element instanceof Page)) {
      _xifexpression = ((Page) element);
    } else {
      _xifexpression = element.getPage();
    }
    Page page = _xifexpression;
    while (((page != null) && (!(page instanceof WebPage)))) {
      {
        result.add(0, page);
        Page _xifexpression_1 = null;
        EObject _eContainer = page.eContainer();
        if ((_eContainer instanceof Page)) {
          EObject _eContainer_1 = page.eContainer();
          _xifexpression_1 = ((Page) _eContainer_1);
        } else {
          _xifexpression_1 = null;
        }
        page = _xifexpression_1;
      }
    }
    return result;
  }
  
  public List<Page> mainPages(final AbstractHTML element) {
    List<Page> result = new ArrayList<Page>();
    if ((!(element instanceof WebPage))) {
      MainPage page = null;
      if ((!(element instanceof MainPage))) {
        page = element.getMainPage();
      } else {
        page = ((MainPage) element);
      }
      result.addAll(page.getMainPages());
      result.addAll(page.getAdditionalPages());
    }
    return result;
  }
  
  @Override
  public String rootNavBarContent() {
    StringConcatenation _builder = new StringConcatenation();
    {
      WebPage _webPage = this.fragment.getWebPage();
      boolean _tripleNotEquals = (_webPage != null);
      if (_tripleNotEquals) {
        _builder.append("<li class=\"nav-item btn btn-outline-light\">");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("<a class=\"nav-link text-white\" href=\"");
        String _url = this.getWebPage().getUrl();
        _builder.append(_url, "\t");
        _builder.append("\">");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        String _icon = this.getIcon(this.getWebPage());
        _builder.append(_icon, "\t\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        String _compiledNavName = this.fragment.getWebPage().compiledNavName();
        _builder.append(_compiledNavName, "\t\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("</a>");
        _builder.newLine();
        _builder.append("</li>");
        _builder.newLine();
        {
          EList<MainPage> _mainPages = this.getWebPage().getMainPages();
          for(final MainPage page : _mainPages) {
            _builder.append("<li class=\"nav-item btn btn-outline-light ");
            String _navbarItemClass = this.getNavbarItemClass(page);
            _builder.append(_navbarItemClass);
            _builder.append("\">");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("<a class=\"nav-link text-white ");
            String _xifexpression = null;
            boolean _containsActiveElement = BootstrapTreeMenuHelper.containsActiveElement(page, this.fragment);
            if (_containsActiveElement) {
              _xifexpression = "active";
            } else {
              _xifexpression = "";
            }
            _builder.append(_xifexpression, "\t");
            _builder.append("\" href=\"");
            String _url_1 = page.getUrl();
            _builder.append(_url_1, "\t");
            _builder.append("\">");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            String _icon_1 = this.getIcon(page);
            _builder.append(_icon_1, "\t\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            String _compiledNavName_1 = page.compiledNavName();
            _builder.append(_compiledNavName_1, "\t\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("</a>");
            _builder.newLine();
            _builder.append("</li>");
            _builder.newLine();
          }
        }
        {
          EList<Page> _additionalPages = this.getWebPage().getAdditionalPages();
          for(final Page page_1 : _additionalPages) {
            _builder.append("<li class=\"nav-item btn btn-outline-light ml-auto ");
            String _navbarItemClass_1 = this.getNavbarItemClass(page_1);
            _builder.append(_navbarItemClass_1);
            _builder.append("\">");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("<a class=\"nav-link text-white ");
            String _xifexpression_1 = null;
            boolean _containsActiveElement_1 = BootstrapTreeMenuHelper.containsActiveElement(page_1, this.fragment);
            if (_containsActiveElement_1) {
              _xifexpression_1 = "active";
            } else {
              _xifexpression_1 = "";
            }
            _builder.append(_xifexpression_1, "\t");
            _builder.append("\" href=\"");
            String _url_2 = page_1.getUrl();
            _builder.append(_url_2, "\t");
            _builder.append("\">");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            String _icon_2 = this.getIcon(page_1);
            _builder.append(_icon_2, "\t\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            String _compiledNavName_2 = page_1.compiledNavName();
            _builder.append(_compiledNavName_2, "\t\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("</a>");
            _builder.newLine();
            _builder.append("</li>");
            _builder.newLine();
          }
        }
      }
    }
    return _builder.toString();
  }
  
  public String getNavbarItemClass(final AbstractHTML menuItem) {
    if ((BootstrapTreeMenuHelper.containsActiveElement(menuItem, this.fragment) || Objects.equal(this.fragment, menuItem))) {
      return "active";
    }
    return "";
  }
  
  @Override
  public String additionalRooNavbarContent() {
    List<Page> breadCrumbs = this.getMainBreadCrumbs(this.fragment);
    List<Page> mainPages = this.mainPages(this.fragment);
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((!(this.fragment instanceof WebPage))) {
        _builder.append("<nav class=\"navbar navbar-expand-sm navbar-light navbar-breadcrumbs\" aria-label=\"breadcrumb\">");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("<ul class=\"navbar-nav\">");
        _builder.newLine();
        {
          for(final Page page : breadCrumbs) {
            _builder.append("\t\t");
            _builder.append("<li class=\"nav-item btn btn-outline ml-auto ");
            {
              int _indexOf = breadCrumbs.indexOf(page);
              int _size = breadCrumbs.size();
              int _minus = (_size - 1);
              boolean _equals = (_indexOf == _minus);
              if (_equals) {
                _builder.append(" active\" aria-current=\"page\"");
              } else {
                _builder.append("\"");
              }
            }
            _builder.append(">");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("<a class=\"nav-link text-tud\" href=\"");
            String _url = page.getUrl();
            _builder.append(_url, "\t\t\t");
            _builder.append("\">");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t\t");
            String _icon = this.getIcon(page);
            _builder.append(_icon, "\t\t\t\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t\t");
            String _compiledNavName = page.compiledNavName();
            _builder.append(_compiledNavName, "\t\t\t\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("</a>");
            _builder.newLine();
            {
              int _indexOf_1 = breadCrumbs.indexOf(page);
              int _size_1 = breadCrumbs.size();
              int _minus_1 = (_size_1 - 1);
              boolean _lessThan = (_indexOf_1 < _minus_1);
              if (_lessThan) {
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("<svg width=\"1.5em\" height=\"2em\" viewBox=\"4 0 10 16\" class=\"bi bi-chevron-compact-right breadcrumb-separator\" fill=\"currentColor\" xmlns=\"http://www.w3.org/2000/svg\">");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("  ");
                _builder.append("<path fill-rule=\"evenodd\" d=\"M6.776 1.553a.5.5 0 0 1 .671.223l3 6a.5.5 0 0 1 0 .448l-3 6a.5.5 0 1 1-.894-.448L9.44 8 6.553 2.224a.5.5 0 0 1 .223-.671z\"/>");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("</svg>");
                _builder.newLine();
              }
            }
            _builder.append("\t\t");
            _builder.append("</li>");
            _builder.newLine();
          }
        }
        _builder.append("\t");
        _builder.append("</ul>");
        _builder.newLine();
        _builder.append("</nav>");
        _builder.newLine();
      }
    }
    {
      boolean _isEmpty = mainPages.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("<nav class=\"navbar navbar-expand-sm navbar-dark bg-tu2 navbar-sub\">");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("<ul class=\"navbar-nav\">");
        _builder.newLine();
        {
          for(final Page page_1 : mainPages) {
            _builder.append("\t\t");
            _builder.append("<li class=\"nav-item btn btn-outline-light ml-auto\">");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("<a class=\"nav-link text-white\" href=\"");
            String _url_1 = page_1.getUrl();
            _builder.append(_url_1, "\t\t\t");
            _builder.append("\">");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t\t");
            String _icon_1 = this.getIcon(page_1);
            _builder.append(_icon_1, "\t\t\t\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t\t");
            String _compiledNavName_1 = page_1.compiledNavName();
            _builder.append(_compiledNavName_1, "\t\t\t\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("</a>");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("</li>");
            _builder.newLine();
          }
        }
        _builder.append("\t");
        _builder.append("</ul>");
        _builder.newLine();
        _builder.append("</nav>");
        _builder.newLine();
      }
    }
    return _builder.toString();
  }
  
  @Override
  public boolean suppressNavBar() {
    boolean _xifexpression = false;
    Page _asPage = this.asPage();
    boolean _tripleNotEquals = (_asPage != null);
    if (_tripleNotEquals) {
      _xifexpression = this.asPage().isSuppressMainMenu();
    } else {
      _xifexpression = this.getHtmlFragment().getPage().isSuppressMainMenu();
    }
    return _xifexpression;
  }
  
  @Override
  public String mainContent() {
    String content = this.getValueContent(this.getHtmlFragment().getContent());
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<div class=\"col\">");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<div class=\"row justify-content-end\">\t\t\t\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    String _beforeContent = this.beforeContent();
    _builder.append(_beforeContent, "\t\t");
    _builder.newLineIfNotEmpty();
    {
      if ((content != null)) {
        _builder.append("\t\t");
        _builder.append(content, "\t\t");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t\t");
        _builder.append("[NO CONTENT]");
        _builder.newLine();
      }
    }
    _builder.append("\t\t");
    String _belowContent = this.belowContent();
    _builder.append(_belowContent, "\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("</div>");
    _builder.newLine();
    _builder.append("\t");
    String _printMetaData = this.printMetaData(this.getHtmlFragment());
    _builder.append(_printMetaData, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("</div>");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String belowContent() {
    return this.printAnnouncements(this.fragment.getCompiledAnnouncements(AnnouncementLocationEnum.BELOW_CONTENT));
  }
  
  public String beforeContent() {
    return this.printAnnouncements(this.fragment.getCompiledAnnouncements(AnnouncementLocationEnum.ABOVE_CONTENT));
  }
  
  @Override
  public String beforeFooter() {
    return this.printAnnouncements(this.fragment.getCompiledAnnouncements(AnnouncementLocationEnum.ABOVE_FOOTER));
  }
  
  @Override
  public String beforeHeader() {
    return this.printAnnouncements(this.fragment.getCompiledAnnouncements(AnnouncementLocationEnum.ABOVE_PAGE));
  }
  
  @Override
  public String belowFooter() {
    return this.printAnnouncements(this.fragment.getCompiledAnnouncements(AnnouncementLocationEnum.BELOW_FOOTER));
  }
  
  @Override
  public String beforeTitle() {
    return this.printAnnouncements(this.fragment.getCompiledAnnouncements(AnnouncementLocationEnum.ABOVE_HEADING));
  }
  
  @Override
  public String belowTitle() {
    return this.printAnnouncements(this.fragment.getCompiledAnnouncements(AnnouncementLocationEnum.BELOW_HEADING));
  }
  
  @Override
  public String getPageTitleText() {
    StringConcatenation _builder = new StringConcatenation();
    String _title = this.getHtmlFragment().getTitle();
    _builder.append(_title);
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  @Override
  public String title() {
    StringConcatenation _builder = new StringConcatenation();
    String _title = this.getHtmlFragment().getTitle();
    _builder.append(_title);
    return _builder.toString();
  }
  
  @Override
  public boolean useNavigation() {
    boolean _xifexpression = false;
    SubPage _asSubPage = this.asSubPage();
    boolean _tripleNotEquals = (_asSubPage != null);
    if (_tripleNotEquals) {
      _xifexpression = ((!this.asSubPage().getSubPage().isEmpty()) || 
        (this.asSubPage().eContainingFeature() == WebPageModelPackage.Literals.SUB_PAGE__SUB_PAGE));
    } else {
      _xifexpression = false;
    }
    return _xifexpression;
  }
}
