/**
 * requests for WikiMeta
 */
package org.zoneproject.extractor.plugin.wikimeta;

/*
 * #%L
 * ZONE-plugin-WikiMeta
 * %%
 * Copyright (C) 2012 ZONE-project
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zoneproject.extractor.utils.Config;
import org.zoneproject.extractor.utils.Prop;

/**
 *
 * @author Desclaux Christophe <christophe@zouig.org>
 */
public class WikiMetaRequest {
    public static String EntitiesURI = "http://www.wikimeta.org/Entities#";
    
    /*public static ArrayList<Prop> getProperties(Item item){
        return getProperties(item.concat());
    }
    
    */
    public static ArrayList<Prop> getProperties(String texte){
        String f = WikiMetaRequest_API.getResult(Config.getVar("wikiMeta-key"), WikiMetaRequest_API.Format.JSON, texte);
        return analyseWikiMetaResult(f);
    }
    
    /**
     * used in debug mode in order to parse a result previously download from WikiMeta
     * @param file the output of WikiMeta
     * @return the list of properties
     */
    public static ArrayList<Prop> getProperties(File file){
        
        String content = "";
        try{
            FileInputStream fstream = new FileInputStream(file.getPath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                content += "\n"+strLine;
            }
            in.close();
        }catch (Exception e){
            Logger.getLogger(WikiMetaRequest.class.getName()).log(Level.SEVERE, null, e);
        }
        return analyseWikiMetaResult(content);
    }
    
    /**
     * 
     * @param content the json result content
     * @return the list of propertyes
     */
    private static ArrayList<Prop> analyseWikiMetaResult(String JSONcontent){
        ArrayList<Prop> result = new ArrayList<Prop>();
        
        ArrayList<LinkedHashMap> namedEntities = WikiMetaRequest_API.getNamedEntities(JSONcontent);
        for(int i=0; i< namedEntities.size();i++){
            
            LinkedHashMap cur = namedEntities.get(i);
            if(cur.get("type").toString().equals("AMOUNT")) {
                continue;
            }
            if(cur.containsKey("LINKEDDATA")){
                Prop p;
                if(cur.get("LINKEDDATA").equals("") || cur.get("LINKEDDATA").equals("null")){
                    p = new Prop(WikiMetaRequest.EntitiesURI+""+cur.get("type").toString(), cur.get("EN").toString(), true);
                }
                else {
                    p = new Prop(WikiMetaRequest.EntitiesURI+""+cur.get("type").toString(), cur.get("LINKEDDATA").toString(), false);
                }
                if(!result.contains(p)) {
                    result.add(p);
                }
            }
        }
        return result;
    }
    
}
