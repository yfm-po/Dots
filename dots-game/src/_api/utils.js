export const formatDate = date => {
		return date.toISOString().slice(0, date.toISOString().length - 1) + "+00:00";
}
