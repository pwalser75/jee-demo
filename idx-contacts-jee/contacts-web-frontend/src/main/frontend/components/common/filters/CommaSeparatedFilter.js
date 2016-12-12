export default function CommaSeparatedFilter () {
	
	return function(text) {
		if (text){
			return text.join(", ");
		}
		return '';
	}
}