package com.example.blockchainms;

import com.example.blockchainms.Dto.DonDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableFeignClients
@RequiredArgsConstructor
@Slf4j
public class BlockchainMsApplication {

	@Autowired
	private JavaMailSender javaMailSender;




	public static void main(String[] args) {
		SpringApplication.run(BlockchainMsApplication.class, args);
	}


	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, DonDTO>> kafkaListenerContainerFactory() {
		JsonDeserializer<DonDTO> jsonDeserializer = new JsonDeserializer<>(DonDTO.class);
		jsonDeserializer.addTrustedPackages("com.example.blockchainms.Dto");

		Map<String, Object> consumerProps = new HashMap<>();
		consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-group");
		consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		ConsumerFactory<String, DonDTO> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps, new StringDeserializer(), jsonDeserializer);

		ConcurrentKafkaListenerContainerFactory<String, DonDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		return factory;
	}

	@KafkaListener(topics = "notificationtopic", groupId = "notification-group")
	public void handleNotification(DonDTO donDTO) {
		try {
			log.info("Received Kafka message:HAMZA MANAOUI {}", donDTO);

			if (!StringUtils.hasText(donDTO.getUserId())) {
				log.error("Invalid user ID for DonDTO: {}", donDTO);
				return;
			}

			sendEmailNotification(donDTO);
		} catch (Exception e) {
			log.error("Error handling Kafka message: {}", e.getMessage(), e);
		}
	}

	private void sendEmailNotification(DonDTO donDTO) {
		// Récupérer l'email du destinataire depuis DonDTO, sinon utiliser l'email par défaut
		String userEmail = "User"+donDTO.getUserId()+"@gmail.com";
		if (!StringUtils.hasText(userEmail)) {

			log.warn("Invalid or missing email address for DonDTO. Using default email: {}", userEmail);
		}


		String senderEmail = "no-reply@yourdomain.com";  // Set the sender email here
		if (!StringUtils.hasText(senderEmail)) {
			log.error("Sender email is not configured.");
			return;
		}

		// Définir le sujet de l'email
		String mailSubject = "Blockchain Notification";

		// Vérifier que les données du don sont valides
		if (donDTO.getOrganisationId() == null || donDTO.getAmount() == null) {
			log.error("Invalid data in DonDTO: Organisation ID or Amount is missing.");
			return;
		}

		// Préparer le message
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setFrom(senderEmail);  // Définir l'expéditeur
			helper.setTo(userEmail);  // Définir le destinataire
			helper.setSubject(mailSubject);  // Définir le sujet
			helper.setText(String.format(
					"Dear User,\n\nA new block has been added to the blockchain:\n\n" +
							"Organisation ID: %s\nAmount: %s\n\nThank you.",
					donDTO.getOrganisationId(),
					donDTO.getAmount()
			));
		};

		// Envoyer l'email
		try {
			javaMailSender.send(messagePreparator);
			log.info("Notification email sent to user: {}", donDTO.getUserId());
		} catch (Exception e) {
			log.error("Failed to send email notification to user: {}", donDTO.getUserId(), e);
		}
	}}




