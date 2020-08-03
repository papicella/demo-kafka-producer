package apples.apj.tanzu.kafka.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class TopicMessageController {

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public TopicMessageController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    final private String topicName = "apples-topic";

    @GetMapping("/")
    public String indexPage (Model model){
        model.addAttribute("topicMessageAddSuccess", "N");
        return "home";
    }

    @PostMapping("/addentry")
    public String addNewTopicMessage (@RequestParam(value="message") String message, Model model){

        kafkaTemplate.send(topicName, message);

        log.info("Sent single message: " + message);
        model.addAttribute("message", message);
        model.addAttribute("topicMessageAddSuccess", "Y");

        return "home";
    }
}
