package ersir.com.game.dao;

import ersir.com.game.abstracts.Element;
import ersir.com.game.bean.Game;
import ersir.com.game.bean.IA;
import ersir.com.game.bean.Player;
import ersir.com.game.bean.Shoot;
import ersir.com.game.global.Difficulties;
import ersir.com.game.global.IAType;
import ersir.com.game.global.IniConfig;
import java.io.*;
import java.util.ArrayList;

public class FileSaving{
	static private ArrayList<Element> listElement = Game.elements;

	public FileSaving() {
		super();
	}

public static void save(){
		StringBuilder buf = new StringBuilder();
		
		for (Element el : listElement) {
			String type="";
			switch(el.getType()){
			case IA:
				type="ia";
			break;
			case Player:
				type="player";
			break;
			case Shoot:
				type="shoot";
			break;
			case Bonus:
				type="bonus";
			break;
			}
			String diff="";
			switch(el.getDifficulty()){
			case Easy:
				diff="easy";
			break;
			case Hard:
				diff="hard";
			break;
			case Hardcore:
				diff="Hardcore";
			break;
			}
			buf.append("").append(el.getId()).append(IniConfig.separator).append(type).append(IniConfig.separator).append(el.getPath()).append(IniConfig.separator).append(el.getX()).append(IniConfig.separator).append(el.getY()).append(IniConfig.separator).append(el.getHp()).append(IniConfig.separator).append(diff).append(IniConfig.line);
		}
		char[] buffer = buf.toString().toCharArray();
		try {
			PrintWriter fos = new PrintWriter(getFile());
			fos.print(buffer);
			System.out.println("Sauvegarde effectu�e dans le fichier " + getFile());
			fos.flush();
			fos.close();
			listElement =  Game.elements;
		} catch (FileNotFoundException e) {
			System.out.println("Impossible de cr�er le fichier...");
		} 
	}

public static void SaveLine(Element newValue){
	int line= -1;
	for(Element el : listElement ){
		line++;
		if(el.getId()==newValue.getId())
		{
			listElement.set(line, newValue);
			el = newValue;
			System.out.println(newValue.getId());
		}
	}
	save();
}

static public File getFile(){
		File saveFile = new File(IniConfig.saveFile);
		if (!saveFile.exists()) {
			try {
				saveFile.createNewFile();
			} catch (IOException e) {
			}
		}
		return saveFile;
	}

static public ArrayList<Element> getElements(){
	int line = -1;
	listElement = new ArrayList<Element>();	
	try{
		InputStream ips=new FileInputStream(IniConfig.saveFile); 
		InputStreamReader ipsr=new InputStreamReader(ips);
		BufferedReader br=new BufferedReader(ipsr);
		String ligne;
		while ( (ligne=br.readLine()) != null){
				line++;
				String type = ligne.split(IniConfig.separator)[1].toString().trim();
				String path = ligne.split(IniConfig.separator)[2].toString().trim();
				float x =  Float.parseFloat(ligne.split(IniConfig.separator)[3].toString().trim());
				float y = Float.parseFloat(ligne.split(IniConfig.separator)[4].toString().trim());
				int hp = Integer.parseInt(ligne.split(IniConfig.separator)[5].toString().trim());
				Difficulties diff = Difficulties.Easy;
				if(type.matches("ia"))
					listElement.add(new IA(path,x,y,hp,diff,IAType.Smart));
				else if(type.matches("player"))
					listElement.add(new Player(path,x,y,hp,diff));
				else if(type.matches("shoot")){
					Shoot shoot = new Shoot(path,x,y,hp,diff,null);
					listElement.add(shoot);	
					Game.shoots.add(shoot);
				}
		}
		br.close(); 
	}		
	catch (Exception e){
		System.out.println(e.toString());
	}	
	debug();
	return listElement;
}
static public boolean isSetElement(Element el){
	for(int j=0; j<getElements().size();j++){
		if(listElement.get(j) == el)
			return true;
	}
	return false;
}
static public void setOption(Element el){
	if(isSetElement(el))
	{		System.out.println("exists");	
		SaveLine(el);
	}
	else{
		System.out.println("add");
		listElement.add(el);	
		save();
	}
}
static public void debug(){
	for(Element option : listElement)
			System.out.println(option.getId()+"->"+option.getX()+"/"+option.getY());		
}

}
