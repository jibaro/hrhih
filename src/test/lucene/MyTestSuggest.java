/**
 * 
 */
package test.lucene;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.suggest.Lookup.LookupResult;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.CharsRef;
import org.apache.lucene.util.UnicodeUtil;
import org.apache.lucene.util.Version;

import com.hrhih.index.analyzer.SplitAnalyzer;
import com.hrhih.index.suggest.Input;
import com.hrhih.index.suggest.InputArrayIterator;

/**
 * @author shuqiang
 * 2014-7-9 下午06:07:33
 */
public class MyTestSuggest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Input keys[] = new Input[] {
			      new Input("中国@zhongguo@zg", 6, new BytesRef("中国@zhongguo@zg")),
			      new Input("中华人民共和国@zhonghuarenmingongheguo@zhrmghg", 10, new BytesRef("中华人民共和国@zhonghuarenmingongheguo@zhrmghg")),
			      new Input("中国人民@zhongguorenmin@zgrm", 5, new BytesRef("中国人民@zhongguorenmin@zgrm")),
			    };
//		Input keys[] = new Input[] {
//				new Input("lend me your ear", 8, new BytesRef("foobar")),
//				new Input("a penny saved is a penny earned", 10, new BytesRef("foobaz")),
//		};

//		Analyzer a = new StandardAnalyzer(Version.LUCENE_4_9);
//		Analyzer a = new KeywordAnalyzer();
		Analyzer a = new SplitAnalyzer('@');
	    AnalyzingInfixSuggester suggester = new AnalyzingInfixSuggester(Version.LUCENE_4_9, new RAMDirectory(), a, a, 3);
	    suggester.build(new InputArrayIterator(keys));

	    List<LookupResult> results = suggester.lookup(stringToCharSequence("guo", new Random()), 10, true, true);
		
	    List<String> retList=new ArrayList<String>();
	    for(LookupResult lr:results){
			retList.add(lr.key.toString().split("@")[0]);
		}
	    System.out.println(retList.size());
	    System.out.println(retList);
	    
	}
	
	  public static CharSequence stringToCharSequence(String string, Random random) {
		    return bytesToCharSequence(new BytesRef(string), random);
	  }
	  
	  public static CharSequence bytesToCharSequence(BytesRef ref, Random random) {
	    switch(random.nextInt(5)) {
	    case 4:
	      CharsRef chars = new CharsRef(ref.length);
	      UnicodeUtil.UTF8toUTF16(ref.bytes, ref.offset, ref.length, chars);
	      return chars;
	    case 3:
	      return CharBuffer.wrap(ref.utf8ToString());
	    default:
	      return ref.utf8ToString();
	    }
	  }

}
