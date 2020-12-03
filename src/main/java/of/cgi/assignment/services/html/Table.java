package of.cgi.assignment.services.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Table {
	private final List<String> columns = new ArrayList<>();
	private final List<List<String>> rows = new ArrayList<>();

	public Table(String... headers) {
		this.columns.addAll(Arrays.asList(headers));
	}

	public Table addRow(List<String> values) {
		rows.add(values);
		return this;
	}

	public String toHtml() {
		StringBuilder tableBuilder = new StringBuilder();
		tableBuilder.append("<table align=\"center\" border=\"1\">");

		// Add table header
		tableBuilder.append("<tr>");
		for (String c : columns) {
			tableBuilder.append("<th>").append(c).append("</th>");
		}
		tableBuilder.append("</tr>");

		// Add table rows
		for (List<String> values : rows) {
			tableBuilder.append("<tr>");
			for (String c : values) {
				tableBuilder.append("<td>").append(c).append("</td>");
			}
			tableBuilder.append("</tr>");
		}

		tableBuilder.append("</table>");
		return tableBuilder.toString();
	}
}
