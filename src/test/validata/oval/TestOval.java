package test.validata.oval;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.configuration.xml.XMLConfigurer;

public class TestOval {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws Exception {
		
		XMLConfigurer xmlConfigurer = new XMLConfigurer(new File(TestOval.class.getResource("").getFile() + "xml\\TrainDataValidation.xml"));
		Validator validator = new Validator(xmlConfigurer);
		
		//(1)对像的单个属性验证
//		Field f=TrainData.class.getDeclaredField("qjStart");
//		List<ConstraintViolation> violations =validator.validateFieldValue(new TrainData(), f, "23");
		
		//(2)整个对象验证
		XmlTrainData data = new XmlTrainData(null,"330327198309078173","","","20050101","2","aa_123",120);
		List<ConstraintViolation> violations = validator.validate(data);
		
		data.setAge(200);
		
		//打印验证结果
		for (ConstraintViolation var : violations) {
			System.out.println(var.getContext()+"======="+var.getMessage());
		}

	}

}
