package application;

import java.util.ArrayList;

/**
 * Write a description of validateWords here.
 * 
 * @author (Aris) 
 * @version (a version number or a date)
 */
public class validateWords {

	
    
    public String isValidFormat(String word) {
      
       StringBuilder sbWord = new StringBuilder(word);
       String newWord = "";
       Character ch = WordBuilderGame.EmptyLabel;
       int i;
       
       for(i = 0; i < sbWord.length() && sbWord.charAt(i) == ch; ++i);
       
       newWord = sbWord.substring(i);
       
       for(i = newWord.length(); i >= 0 && newWord.charAt(i-1) == ch; i--);
       
       newWord = newWord.substring(0,i);
       
              
       if (newWord.length()<3 || newWord.indexOf("-") != -1)
       {
          System.out.println("Invalid word. Try again");
          return "";
       }
       else{
          System.out.println("The word that should be checked if valid = " + newWord);
          return newWord;
       }
    }
    
    
    
    
    
    public String isValidWord( String word) {
        
        String wordForSearch = isValidFormat(word);
        
        if (wordForSearch == "")
        {
           System.out.println("Invalid word. Try again");
           
        }
        else{
           
        	System.out.println("The word that should be checked if valid = " + wordForSearch);
            
            return wordForSearch;
        }
        return "";
    }
    
    
    
    
    public void searchInArrayList( ArrayList<String> wordsBD, String wordForSearch) {
    	
    	
     //   if ()
    }
    
    
    
}