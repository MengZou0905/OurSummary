package code;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * args[0]:The path of the input.
 * 		Single-document summarization task: The path of the input file and this file can only contain one document you want to get the summary from.
 * 		Multi-document summarization task or topic-based multi-document summarization task: The path of the input directory and this directory can only contain one document set you want to get the summary from.
 * args[1]:The path of the output file and one file only contains one summary.
 * args[2]:The language of the document. 1: Chinese, 2: English, 3: other Western languages
 * args[3]:Specify which task to do.
 * 		1: single-document summarization, 2: multi-document summarization,
 * 		3: topic-based multi-document summarization
 * args[4]:The expected number of words in summary.
 * args[5]:Choose if you want to stem the input. (Only for English document) 
 * 		1: stem, 2: no stem, default = 1
 * args[6]:Choose whether you need remove the stop words.
 * 		If you need remove the stop words, you should input the path of stop word list. 
 *  	Or we have prepared an English stop words list as file ��stopword_Eng��, you can use it by input ��y��.
 *   	If you don��t need remove the stop words, please input ��n��.
 * 
 * */

public class Lead {
	public Doc myDoc = new Doc();
    public ArrayList<Integer> summaryId = new ArrayList<>();
    public int sumNum = 0;
    
    public void Summarize(String args[]) throws IOException
    {
    	
    	/* Read files */
    	/*
    	arg[0] = file + System.getProperty("file.separator") + fOrd.getName();
		arg[1] = outFile + System.getProperty("file.separator") + fOrd.getName().replace("utf8", "");
		arg[2] = "2"; // 2-English
		arg[3] = "2"; // single,multi,topic
		arg[4] = "100"; // summary 的长度
		arg[5] = "1"; // stemmer or not
		arg[6] = "y"; // stopword or not
		*/
    	if (args[3].equals("1"))
        {
    		String[] singleFile = new String[1];
            singleFile[0] = args[0];
            myDoc.maxlen = Integer.parseInt(args[4]);
            myDoc.readfile(singleFile, " ", args[2], args[6]);
        }
    	else if (args[3].equals("2")|| args[3].equals("3"))
        {
    		File myfile = new File(args[0]);// 
            myDoc.maxlen = Integer.parseInt(args[4]);
            myDoc.readfile(myfile.list(),args[0],args[2], args[6]);
            // all the files under the dir, dir, language, stopword or not
        }
    	
    	/* Get abstract */
    	int tmp = 0;
        while(sumNum <= myDoc.maxlen && tmp < myDoc.snum) {
        	summaryId.add(tmp);
        	
        	sumNum += myDoc.senLen.get(tmp);
        	tmp++;
        }
        
        /* Output the abstract */
    	try{
    		File outfile = new File(args[1]);
    		OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(outfile),"utf-8");
    		BufferedWriter writer = new BufferedWriter(write);
    		for (int i : summaryId){
    			writer.write(myDoc.originalSen.get(i));
    			writer.write("\n");
            }
    		writer.close();
    	}
    	catch(Exception e){
    		System.out.println("There are errors in the output.");
    		e.printStackTrace();
    	}
    }
}
