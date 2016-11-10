package hyweb.gip.pattern.service;

import java.util.Map;

import org.jdom2.Document;

public interface ShowFormServiceEvent {

		
		public void execute(Document dataXML,Map<String, String[]> req);


}
