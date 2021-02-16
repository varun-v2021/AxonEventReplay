package nl.fourscouts.blog.replays.api;

import lombok.AllArgsConstructor;
import nl.fourscouts.blog.replays.domain.Replayer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.NumberFormat;

@RestController
@AllArgsConstructor
public class ReplayController {
	private Replayer replayer;

	@PostMapping("/replay")
	public String replay() {
		//https://docs.axoniq.io/reference-guide/axon-framework/events/event-processors#event-processors
		//All event handlers are attached to a processor whose name is the package name of the Event Handler's class.
		replayer.replay("nl.fourscouts.blog.replays.projections");

		return "Replay started...";
	}

	@GetMapping("/replay")
	public String progress() {
		return replayer.getProgress("nl.fourscouts.blog.replays.projections").map(progress ->
			String.format("Replay at %d/%d (%s complete)",
				progress.getCurrent(), progress.getTail(), NumberFormat.getPercentInstance().format(progress.getProgress().doubleValue()))
		).orElse("No replay active");
	}
}
