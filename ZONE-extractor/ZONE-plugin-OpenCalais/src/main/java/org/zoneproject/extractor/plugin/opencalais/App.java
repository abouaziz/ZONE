package org.zoneproject.extractor.plugin.opencalais;

/*
 * #%L
 * ZONE-plugin-OpenCalais
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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.zoneproject.extractor.utils.Database;
import org.zoneproject.extractor.utils.Item;
import org.zoneproject.extractor.utils.Prop;

/**
 * Hello world!
 *
 */
public class App 
{
    public static String PLUGIN_URI = "http://www.zone-project.org/plugins/OpenCalais";
    
    public App(){
        String [] tmp = {};
        App.main(tmp);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Item[] items = Database.getItemsNotAnotatedForOnePlugin(PLUGIN_URI);
        System.out.println("OpenCalais has "+items.length+" items to annotate");
        for(Item item : items){
            System.out.println("Add ExtractArticlesContent for item: "+item);
            ArrayList<Prop> props = new ArrayList<Prop>();
            props.addAll(openCalaisExtractor.getCitiesResultProp(item.concat()));
            props.addAll(openCalaisExtractor.getPersonsResultProp(item.concat()));

            Database.addAnnotations(item.getUri(), props);
            Database.addAnnotation(item.getUri(), new Prop(PLUGIN_URI,"true"));
        }
    }
}
