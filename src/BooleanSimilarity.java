import java.io.Serializable;

import org.apache.lucene.index.FieldInvertState;
import org.apache.lucene.search.similarities.DefaultSimilarity;
//import org.apache.lucene.search.similarities.Similarity;


public class BooleanSimilarity extends DefaultSimilarity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BooleanSimilarity(){}

	public float idf(long docFreq, long numDocs) {

		return (float) 0;
	}

	public float tf(float freq) {

		return (float) 0;
	}

	public float coord(int overlap, int maxOverlap) {

		return (float) 0;
	}

	public float queryNorm(float sumOfSquaredWeights) {
		return (float) 1;
	}

}
