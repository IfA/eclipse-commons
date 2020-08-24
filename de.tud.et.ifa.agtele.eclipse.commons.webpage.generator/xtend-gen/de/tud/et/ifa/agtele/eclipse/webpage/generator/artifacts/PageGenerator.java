package de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts;

import com.google.common.base.Objects;
import de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts.AbstractPageGenerator;
import de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts.BootstrapHtmlGenerator;
import de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts.BootstrapTreeMenuHelper;
import de.tud.et.ifa.agtele.eclipse.webpage.util.IStringSubstitutor;
import de.tud.et.ifa.agtele.eclipse.webpage.util.ResultReporter;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML;
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
    _builder.append("<h4>Contents</h4>");
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
    Page page = element.getPage();
    while ((!this.isRootNode(page))) {
      {
        result.add(0, page);
        Page _xifexpression = null;
        EObject _eContainer = page.eContainer();
        if ((_eContainer instanceof Page)) {
          EObject _eContainer_1 = page.eContainer();
          _xifexpression = ((Page) _eContainer_1);
        } else {
          _xifexpression = null;
        }
        page = _xifexpression;
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
    List<Page> breadCrumbs = this.getMainBreadCrumbs(this.fragment);
    List<Page> mainPages = this.mainPages(this.fragment);
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
        String _name = this.fragment.getWebPage().getName();
        _builder.append(_name, "\t\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("</a>");
        _builder.newLine();
        _builder.append("</li>");
        _builder.newLine();
        {
          EList<MainPage> _mainPages = this.getWebPage().getMainPages();
          for(final MainPage page : _mainPages) {
            _builder.append("<li class=\"nav-item btn btn-outline-light\">");
            _builder.newLine();
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
            String _name_1 = page.getName();
            _builder.append(_name_1, "\t\t");
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
            _builder.append("<li class=\"nav-item btn btn-outline-light ml-auto\">");
            _builder.newLine();
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
            String _name_2 = page_1.getName();
            _builder.append(_name_2, "\t\t");
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
    {
      if (((!this.isRootNode(this.fragment)) && (!this.isRootNode(this.fragment.getPage())))) {
        _builder.append("<br/>");
        _builder.newLine();
        _builder.append("<nav class=\"navbar navbar-expand-sm navbar-light bg-tu\">");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("<ul class=\"navbar-nav navbar-breadcrumbs\">");
        _builder.newLine();
        {
          for(final Page page_2 : breadCrumbs) {
            _builder.append("\t\t");
            _builder.append("<li class=\"nav-item btn btn-outline-light ml-auto\">");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("<a class=\"nav-link text-white\" href=\"");
            String _url_3 = page_2.getUrl();
            _builder.append(_url_3, "\t\t\t");
            _builder.append("\">");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t\t");
            String _icon_3 = this.getIcon(page_2);
            _builder.append(_icon_3, "\t\t\t\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t\t");
            String _name_3 = page_2.getName();
            _builder.append(_name_3, "\t\t\t\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("</a>");
            _builder.newLine();
            {
              int _indexOf = breadCrumbs.indexOf(page_2);
              int _size = breadCrumbs.size();
              int _minus = (_size - 1);
              boolean _lessThan = (_indexOf < _minus);
              if (_lessThan) {
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("<svg width=\"1em\" height=\"1em\" viewBox=\"0 0 16 16\" class=\"bi bi-chevron-compact-right\" fill=\"currentColor\" xmlns=\"http://www.w3.org/2000/svg\">");
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
        _builder.append("<br/>");
        _builder.newLine();
        _builder.append("<nav class=\"navbar navbar-expand-sm navbar-dark bg-tu-light\">");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("<ul class=\"navbar-nav navbar-main\">");
        _builder.newLine();
        {
          for(final Page page_3 : mainPages) {
            _builder.append("\t\t");
            _builder.append("<li class=\"nav-item btn btn-outline-light ml-auto\">");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("<a class=\"nav-link text-white\" href=\"");
            String _url_4 = page_3.getUrl();
            _builder.append(_url_4, "\t\t\t");
            _builder.append("\">");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t\t");
            String _icon_4 = this.getIcon(page_3);
            _builder.append(_icon_4, "\t\t\t\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t\t");
            String _name_4 = page_3.getName();
            _builder.append(_name_4, "\t\t\t\t");
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
    _builder.append("<h2>");
    String _pageTitleText = this.getPageTitleText();
    _builder.append(_pageTitleText, "\t");
    _builder.append("</h2>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<div class=\"row justify-content-end\">");
    _builder.newLine();
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
  
  @Override
  public String getPageTitleText() {
    StringConcatenation _builder = new StringConcatenation();
    String _title = this.getHtmlFragment().getTitle();
    _builder.append(_title);
    _builder.newLineIfNotEmpty();
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
