/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import entity.Form;
import entity.Letter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.prefs.Preferences;

/**
 *
 * @author Yan Kaic
 */
public class LetterMananger {

  public static void main(String[] args) {
    new LetterMananger().load();
  }
  private final String complete;
  private final List<String> pendencesList;

  public LetterMananger() {
    complete = "~e,~i,~u,a,â,ã,b,d,é,ê,f,g,i,j,k,l,lh,m,n,nh,ó,ô,õ,p,r,rr,s,t,u,v,x,z,";
    pendencesList = new ArrayList<>();
  }

  public void load() {
    Preferences preferences = Preferences.userNodeForPackage(LetterMananger.class);
    String pendences = preferences.get("pendences", null);
    if (pendences == null) {
      pendences = complete + complete.toUpperCase();
    }
    toList(pendences);
    Collections.shuffle(pendencesList);
  }

  private void toList(String pendences) {
    String letter;
    while (pendences.length() > 0) {
      int index = pendences.indexOf(",");
      letter = pendences.substring(0, index);
      pendences = pendences.substring(index + 1, pendences.length());
      pendencesList.add(letter);
    }
  }

  public void reload() {
    String pendences = complete + complete.toUpperCase();
    toList(pendences);
    Collections.shuffle(pendencesList);
  }

  public void save() {
    String pendences = "";
    for (String letter : pendencesList) {
      pendences += letter + ',';
    }

    Preferences preferences = Preferences.userNodeForPackage(LetterMananger.class);
    preferences.put("pendences", pendences);
  }

  public Letter nextLetter() {
    if (pendencesList.isEmpty()) {
      reload();
    }
    String name = pendencesList.remove(0);
    save();
    return new Letter(name);
  }

  public Form[] getForms(int size) {
    if (pendencesList.size() < size) {
      reload();
    }
    Form[] forms = new Form[size];
    for (int i = 0; i < forms.length; i++) {
      forms[i] = new Form(new Letter(pendencesList.get(i)));
    }
    return forms;
  }

}
