package seamshop.util;

import org.apache.struts2.interceptor.parameter.StrutsParameter;

/**
 * Represents a result of purifying HTML of XSS.
 *
 * @see HtmlUtils#filterFromXss(String)
 * @see HtmlUtils#getPlainText(String)
 *
 * @author Alex Siman 2009-10-09
 */
public class SafeHtmlAndText
{
	private String html = null;
	private String text = null;

	public SafeHtmlAndText()
	{}

	public SafeHtmlAndText(String html, String text)
	{
		this();
		this.html = html;
		this.text = text;
	}

	public String getHtml()
	{
		return html;
	}

	@StrutsParameter
	public void setHtml(String html)
	{
		this.html = html;
	}

	public String getText()
	{
		return text;
	}

	@StrutsParameter
	public void setText(String text)
	{
		this.text = text;
	}
}
