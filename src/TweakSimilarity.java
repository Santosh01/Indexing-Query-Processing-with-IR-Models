import java.io.Serializable;

import org.apache.lucene.index.FieldInvertState;
import org.apache.lucene.search.similarities.DefaultSimilarity;
//import org.apache.lucene.search.similarities.Similarity;


public class TweakSimilarity extends DefaultSimilarity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TweakSimilarity(){}

	public float idf(long docFreq, long numDocs) {

		//Default logarithmic
		return (float)(Math.log(numDocs/(double)(docFreq+1)) + 1.0);
		
		//Square root
//		return (float)(Math.sqrt(numDocs/(double)(docFreq)));
		
		//No IDF
//		return 1;
		
		//Probabilistic IDF6
//		return (float)(Math.log(((numDocs)-docFreq)/(double)(docFreq)));
	}

	public float tf(float freq) {

		//Default i.e Square root
		return (float)Math.sqrt(freq);
		
		// Natural
//		return (float) freq;
		
		// Logarithm
//		if(freq > 0)
//		return (float)(Math.log(freq) + 1.0);
//		else 
//		return 0;
		// Boolean
//		return freq > 0.0 ? (float)1 : (float)0;
		
		
	}

	

}
