
package computerorganization.project;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Scanner;

public class ComputerOrganizationPROJECT {
    

    public static String Converter (int Number,int n) {
        int Arr[] = new int [n];
        int i;
        String x=new String();
        String y=new String();
   
        for(i=0; Number > 0; i++) {
            Arr[i] = Number % 2;
            Number = Number /2;
        }
        for(i=i-1;i >= 0;i--) {
            x+= Arr[i];
        }
        int diff=n-x.length();
        if (diff > 0)
        {
            for (i=0 ;i <diff;i++)
             y+="0";
        }
        return y+x;
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        int w,c,b,k,Tag,Set,Word,count=0,NoOfBlocks,WordsPerLine,NoOfLines; 
   
  File MyFile = new File("C:\\Users\\user\\Downloads\\input files\\input files\\08-ohaa2-fullassoc.in");
  FileReader fr = new FileReader(MyFile);
  Scanner sc = new Scanner(MyFile);
        LineNumberReader lr = new LineNumberReader(fr);
        while (lr.readLine() != null)
        {
            count++;
        }
        count+=3;
  int arr[]= new int [count]; 
  int I=0;
  while (sc.hasNext())
 {
     arr[I]=sc.nextInt();
     I++;
 }
sc.close();
w = arr[0];
c = arr[1];
b = arr[2];
k = arr[3]; 


 NoOfBlocks = c / b;
 WordsPerLine = b / k;
 NoOfLines=NoOfBlocks * k;
 Set=Math.getExponent(NoOfBlocks);
 Word=Math.getExponent(WordsPerLine);
 Tag= w-(Set+Word);

 String [] bin = new String[arr.length-4];   
 int J=0;
 for (int i=4; i<arr.length;i++)                            
 { bin[J]=Converter(arr[i], w); J++;} // array where numbers are in binary.
 int cache[][]= new int [NoOfLines][WordsPerLine];
 String TagInBinary,SetInBinary,WordInBinary;   // Divide into tag , set , word.
 int [][]SetInDecimalWordInDecimal=new int[bin.length][2]; // array where the set and word of each number are corresponding to each other in decimal.   Set - Word.
 int numbers[][]=new int[arr.length-4][2]; // the number corresponding to the counter of it      Decimal.No - Counter.
 boolean isHit=false;
 
 for (int i=0;i<bin.length;i++) //initilization              
 {  
     TagInBinary=bin[i].substring(0, Tag);  // Tag
     SetInBinary=bin[i].substring(Tag,Tag+Set);   // Set
     WordInBinary=bin[i].substring(Tag+Set);   //Word
     if(WordInBinary.equals(""))
         WordInBinary="0";
     if (SetInBinary.equals(""))
         SetInBinary="0";
     if (TagInBinary.equals(""))
         TagInBinary="0";
     SetInDecimalWordInDecimal[i][0]=Integer.parseInt(SetInBinary,2);              // Set   in decimal to know where to put in cache.
     SetInDecimalWordInDecimal[i][1]=Integer.parseInt(WordInBinary,2);            //  Word in decimal to know where to put to cache.
      int WordNo=SetInDecimalWordInDecimal[i][1];   // Word Location.
     int wrd= arr[i+4]-WordNo;
    numbers[i][0]=wrd;
    numbers[i][1]=0;  
     
 }
 for (int i=0; i <bin.length;i++) 
   {
     int y=0,h;
     int WordNo=SetInDecimalWordInDecimal[i][1];   // Word location.
     int wrd= arr[i+4]-WordNo;                     // No - its location , to know where to start in cache. 
     for (int x=0;x<NoOfLines;x++)     
     { 
         for(int j=0;j<WordsPerLine;j++)   
         {                  
             int z=arr[i+4] - cache[x][j];   // if No in cache <= Wordper line = hit !.
              if (z <0 )
                 z=-z;  
             if (z <= WordsPerLine)
             { 
                 isHit=true;
                  numbers[i][1]=i+1; // counter+1 to the currrent number.
                   
              for(int xy=0;xy<arr.length-4;xy++)      // if the number appears again.
                 {
                   int gg=arr[i+4] - numbers[xy][0];
                   if (gg <0 )
                       gg=-gg;
                  if (gg <= WordsPerLine)
                     numbers[xy][1]=i+1; // counter+1 to the numbers in the same line as the current number
                 } 
                 break;
             }
             else
                isHit=false;
         }         
         if(isHit)
             break;
         if(i+4==bin.length+4)
                 break;
     }
     if (isHit)
     {
         System.out.print("C"+" ");
     }
     if (!isHit)
     { 
      
         System.out.print("M"+" ");
        int LineNo= SetInDecimalWordInDecimal[i][0]*k;
       
        if (cache[LineNo][SetInDecimalWordInDecimal[i][1]]!=0)   // condition to check if first line in the set is not empty 
            for (y=0;y<k;y++)       
           {  
               if (cache[LineNo+y][SetInDecimalWordInDecimal[i][1]]==0) // to check if nxt line is empty 
               {
                   for (int j=0;j<WordsPerLine;j++)
                        cache[LineNo+y][j]=wrd+j;    // FILL the cache.
                      
                   numbers[i][1]=i+1; // counter+1 to the currrent number
              
              for(int xy=0;xy<arr.length-4;xy++)
                 {
                   int z=arr[i+4] - numbers[xy][0];
                   if (z <0 )
                       z=-z;
                  if (z <= WordsPerLine)
                     numbers[xy][1]=i+1; // counter+1 to the numbers in the same line as the current number
                 } 
                  break;
               }
               if (y==k)   // Lines are full.
                   break;
           }
         // LRU
           if (y==k) // if all lines are not empty 
           {
              int L,g,a=0,min=9999;
             
                for(int p=0;p<k;p++)
              { 
                  L= cache[LineNo+p][0]; // store value of a number in each line 
                   for (h = 0; h < arr.length-4; h++) // for loop to fin minimum value of counters
                    if (numbers[h][0]==L) // check if this number in the array 
                    {
                        g=numbers[h][1];
                        if (g < min)
                        { min=g;// 
                          a=p ; // to find the index where minimum counter present 
                        }
                    }  
              }
               for (int j=0;j<WordsPerLine;j++)
               {  cache[LineNo+a][j]=wrd+j; } // add to cache
                    numbers[i][1]=i+1;
                 for(int xy=0;xy<arr.length-4;xy++)
                 {
                   int z=arr[i+4] - numbers[xy][0];
                   if (z <0 )
                       z=-z;
                  if (z <= WordsPerLine)
                     numbers[xy][1]=i+1;
                 }                     
           }
           else if (cache[LineNo][SetInDecimalWordInDecimal[i][1]]==0) // if the line is empty insert
           {
               for (int j=0;j<WordsPerLine;j++)
                cache[LineNo][j]=wrd+j;
                      numbers[i][1]=i+1;
                  for(int xy=0;xy<arr.length-4;xy++)
                 {
                   int z=arr[i+4] - numbers[xy][0];
                   if (z <0 )
                       z=-z;
                  if (z <= WordsPerLine)
                     numbers[xy][1]=i+1;
                 } 
           }
     }
 
 }   
 }   
}

