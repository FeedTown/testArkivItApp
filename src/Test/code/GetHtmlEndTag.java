package Test.code;

public class GetHtmlEndTag {

	private String endTagLink = "</link>", endTagImg ="</img>" , endTagScript = "</script>" , endTagHtmlLink = "</a>";
	private String linkTagType = "type=\"", relTagType ="rel=\"", idTagType = "id=\"", altTagType="alt=\"", widthTagType="width=\""
				  , heightTagType ="height=\"";
	private String href="href=", src = "src=";
	
	//private String currentWord;

	
	public GetHtmlEndTag() {
		//this.currentWord = currentWord;
	}
	
	public GetHtmlEndTag(String currentWord) {
		//this.currentWord = currentWord;
	}
	
	public boolean getTagType(String currentWord)
	{
		String currWord = "";
		if(currentWord.contains(linkTagType))
		{
			currWord = linkTagType;
			return true;
		}
		else if(currentWord.contains(relTagType))
		{
			currWord = relTagType;
			return true;
		}
		else if(currentWord.contains(idTagType))
		{
			currWord = idTagType;
			return true;
		}
		else if(currentWord.contains(altTagType))
		{
			currWord = altTagType;
			return true;
		}
		else if(currentWord.contains(widthTagType))
		{
			currWord = widthTagType;
			return true;
		}
		else if(currentWord.contains(heightTagType))
		{
			currWord = heightTagType;
			return true;
		}
		
		return false;
	}

	public boolean hasEndLink(String currentWord)
	{
		if(currentWord.contains(endTagScript))
		{
			return true;
		}
		else if(currentWord.contains(endTagLink)){
			return true;
		}
		else if(currentWord.contains(endTagImg)){
			return true;
		}
		else if(currentWord.contains(endTagHtmlLink)){
			return true;
		}
		else if(currentWord.contains(linkTagType))
		{
			return true;
		}


		return false;
	}

	public String getLinkRef(String currentWord)
	{
		String linkRef = "";
		
		if(currentWord.contains(href))
		{
			linkRef = href;
		}
		else if(currentWord.contains(src))
		{
			linkRef = src;
		}
		
		return linkRef;
	}
	
	public String getLinkRefTest(String currentWord, int splitedNum)
	{
		String linkRef = "";
		
		if(currentWord.contains(href))
		{
			linkRef = href;
		}
		else if(currentWord.contains(src))
		{
			linkRef = src;
		}
		else if(splitedNum == 2)
		{
			linkRef = "\"";
		}
		else if(splitedNum == 3)
		{
			linkRef = "/";
		}
		
		return linkRef;
	}
}
