package of.cgi.assignment.services.html;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class TableTest {

	@Test
	public void toHtml() {
		Table t = new Table("First Name", "Last Name");
		t.addRow(Arrays.asList("Samiron", "Paul"))
				.addRow(Arrays.asList("Paul", "Mathew"))
				.addRow(Arrays.asList("Another", "Person"));

		String response = t.toHtml();
		assertThat(response, is(equalTo("<table><tr><th>First Name</th><th>Last Name</th></tr><tr><td>Samiron</td><td>Paul</td></tr><tr><td>Paul</td><td>Mathew</td></tr><tr><td>Another</td><td>Person</td></tr></table>")));
	}

}