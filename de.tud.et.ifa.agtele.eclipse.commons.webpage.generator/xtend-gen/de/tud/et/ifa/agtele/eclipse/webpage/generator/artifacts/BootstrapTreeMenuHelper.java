package de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts;

import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Page;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.SubPage;
import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class BootstrapTreeMenuHelper {
  private int collapseCounter = 0;
  
  protected AbstractHTML element;
  
  protected Page localRoot;
  
  public BootstrapTreeMenuHelper(final Page localRoot, final AbstractHTML element) {
    this.element = element;
    this.localRoot = localRoot;
  }
  
  public boolean isNode(final SubPage page) {
    boolean _isEmpty = page.getSubPage().isEmpty();
    return (!_isEmpty);
  }
  
  public List<SubPage> getChildren(final SubPage element) {
    return element.getSubPage();
  }
  
  public static boolean containsActiveElement(final AbstractHTML parent, final AbstractHTML page) {
    return AgteleEcoreUtil.getAllContainers(parent).contains(page);
  }
  
  public boolean containsActiveElement(final AbstractHTML page) {
    return BootstrapTreeMenuHelper.containsActiveElement(page, this.element);
  }
  
  public String createMenu() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<ul class=\"nav nav-list\">");
    _builder.newLine();
    _builder.append("\t");
    String _createNodeChildren = this.createNodeChildren(this.localRoot);
    _builder.append(_createNodeChildren, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("</ul>");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String createNavElement(final SubPage element) {
    boolean _isNode = this.isNode(element);
    if (_isNode) {
      return this.createNode(element);
    }
    return this.createLeaf(element);
  }
  
  public String createNodeChildren(final SubPage node) {
    StringConcatenation _builder = new StringConcatenation();
    {
      final Function1<SubPage, String> _function = (SubPage c) -> {
        String _elvis = null;
        String _name = c.getName();
        if (_name != null) {
          _elvis = _name;
        } else {
          _elvis = "";
        }
        return _elvis;
      };
      List<SubPage> _sortBy = IterableExtensions.<SubPage, String>sortBy(this.getChildren(node), _function);
      for(final SubPage c : _sortBy) {
        String _createNavElement = this.createNavElement(c);
        _builder.append(_createNavElement);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder.toString();
  }
  
  public String getClass(final SubPage node) {
    String result = "";
    boolean _isNode = this.isNode(node);
    if (_isNode) {
      boolean _not = (!(this.containsActiveElement(node) || (node == this.element)));
      if (_not) {
        String _result = result;
        result = (_result + " collapsed");
      }
    }
    if (((node == this.element) || this.containsActiveElement(node))) {
      String _result_1 = result;
      result = (_result_1 + " active");
    }
    return result;
  }
  
  public String createNode(final SubPage node) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<li class=\"tree-node\">");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<a class=\"");
    String _class = this.getClass(node);
    _builder.append(_class, "\t");
    _builder.append(" btn btn-outline-dark tree-node-button");
    {
      boolean _contains = this.getClass(node).contains("active");
      if (_contains) {
        _builder.append(" active");
      }
    }
    _builder.append("\" data-id=\"");
    String _id = node.getId();
    _builder.append(_id, "\t");
    _builder.append("\" data-toggle=\"collapse\" role=\"button\" href=\"#collapsible");
    _builder.append(this.collapseCounter, "\t");
    _builder.append("\" >");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("<span class=\"inline-block\">");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<svg class=\"bi bi-chevron-right\" width=\"1.5em\" height=\"1.5em\" viewBox=\"0 0 16 16\" fill=\"currentColor\" xmlns=\"http://www.w3.org/2000/svg\">");
    _builder.newLine();
    _builder.append("\t\t\t  ");
    _builder.append("<path fill-rule=\"evenodd\" d=\"M4.646 1.646a.5.5 0 01.708 0l6 6a.5.5 0 010 .708l-6 6a.5.5 0 01-.708-.708L10.293 8 4.646 2.354a.5.5 0 010-.708z\" clip-rule=\"evenodd\"/>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("</svg>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<svg class=\"bi bi-chevron-down\" width=\"1.5em\" height=\"1.5em\" viewBox=\"0 0 16 16\" fill=\"currentColor\" xmlns=\"http://www.w3.org/2000/svg\">");
    _builder.newLine();
    _builder.append("\t\t\t  ");
    _builder.append("<path fill-rule=\"evenodd\" d=\"M1.646 4.646a.5.5 0 01.708 0L8 10.293l5.646-5.647a.5.5 0 01.708.708l-6 6a.5.5 0 01-.708 0l-6-6a.5.5 0 010-.708z\" clip-rule=\"evenodd\"/>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("</svg>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("</span>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</a>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<a class=\"btn btn-link tree-node-link");
    {
      boolean _contains_1 = this.getClass(node).contains("active");
      if (_contains_1) {
        _builder.append(" active");
      }
    }
    _builder.append("\" role=\"button\" href=\"");
    String _url = node.getUrl();
    _builder.append(_url, "\t");
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    String _name = node.getName();
    _builder.append(_name, "\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("</a>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<div class=\"collapse");
    {
      boolean _contains_2 = this.getClass(node).contains("collapsed");
      boolean _not = (!_contains_2);
      if (_not) {
        _builder.append(" show");
      }
    }
    _builder.append("\" id=\"collapsible");
    int _plusPlus = this.collapseCounter++;
    _builder.append(_plusPlus, "\t");
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("<ul class=\"nav nav-list\">");
    _builder.newLine();
    _builder.append("\t   \t\t");
    String _createNodeChildren = this.createNodeChildren(node);
    _builder.append(_createNodeChildren, "\t   \t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("</ul>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</div>");
    _builder.newLine();
    _builder.append("</li>");
    _builder.newLine();
    _builder.append("<li class=\"divider\"></li>");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String createLeaf(final SubPage element) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<li data-id=\"");
    String _id = element.getId();
    _builder.append(_id);
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<a class=\"");
    String _class = this.getClass(element);
    _builder.append(_class, "\t");
    _builder.append(" btn  btn-link tree-link\" href=\"");
    String _url = element.getUrl();
    _builder.append(_url, "\t");
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("<svg width=\".5em\" height=\".5em\" viewBox=\"0 0 16 16\" class=\"bi bi-circle-fill\" fill=\"currentColor\" xmlns=\"http://www.w3.org/2000/svg\">");
    _builder.newLine();
    _builder.append("\t\t  ");
    _builder.append("<circle cx=\"8\" cy=\"8\" r=\"8\"/>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("</svg>");
    _builder.newLine();
    _builder.append("\t\t");
    String _name = element.getName();
    _builder.append(_name, "\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("</a>");
    _builder.newLine();
    _builder.append("</li>");
    _builder.newLine();
    return _builder.toString();
  }
}
