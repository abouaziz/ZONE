package org.zoneproject.extractor.plugin.categorization_svm.model;

/*
 * #%L
 * ZONE-plugin-categorization_SVM
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
import java.io.Serializable;

public class Mot implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public String Lemma;
    public int nbOccuences;
    public Text text;
    public int rankInDic;
    public double weight;

    public Mot() {
        this.nbOccuences = 0;
        this.rankInDic = 0;
        this.weight = 0;
    }

    public String toString() {
        return "{mot:  Lemma=" + Lemma + ", weight=" + weight + ", nbOccuences=" + nbOccuences + "}";
    }
}
