package idx.persistence.annotation;

import javax.enterprise.util.AnnotationLiteral;

public class SpecificLiteral extends AnnotationLiteral<Specific> implements Specific {
	private Class<?> type;

	public SpecificLiteral(Class<?> type) {
		this.type = type;
	}

	@Override
	public Class<?> type() {
		return type;
	}

}