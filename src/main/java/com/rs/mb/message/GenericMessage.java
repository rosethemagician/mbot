package com.rs.mb.message;

import com.github.twitch4j.TwitchClient;
import com.rs.mb.domain.entities.Lembrete;
import com.rs.mb.domain.entities.Usuario;
import com.rs.mb.services.LembreteService;
import com.rs.mb.services.MensagemService;
import com.rs.mb.services.UsuarioService;
import com.rs.mb.utils.MbUtilsData;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GenericMessage {

    private final UsuarioService usuarioService;

    private final MensagemService mensagemService;

    private final LembreteService lembreteService;

    private final TwitchClient twitchClient;

    @Autowired
    private List<Lembrete> lembretes;

    @Autowired
    private ConcurrentHashMap<String, String> usuariosPendentes;

    public void handleGenericMessage(String channel, Usuario user, String message) {
        System.out.printf(
                "Channel [%s] - User[%s] - Message [%s]%n",
                channel,
                user.getNome(),
                message
        );

        if (usuariosPendentes.containsKey(user.getNome())) {

            List<Lembrete> lembretesDoUsuario = this.lembretes.stream().filter(l -> l.getDestinatario().getNome().equalsIgnoreCase(user.getNome())).collect(Collectors.toList());

            StringBuilder remindMessage = new StringBuilder(user.getNome() + " reminders from ");

            lembretesDoUsuario.stream().forEach(l -> remindMessage.append("[" + l.getRemetente().getNome() + "]: " + l.getConteudo() + " (" + MbUtilsData.generateLastSeenDate(l.getDataCriacao()) + ") "));

            String[] wrappedMessage = WordUtils.wrap(remindMessage.toString(), 170).split(System.lineSeparator());

            for (String s : wrappedMessage) {
                this.twitchClient.getChat().sendMessage(channel, s);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            usuariosPendentes.remove(user.getNome());
            lembretes.removeAll(lembretesDoUsuario);
            this.lembreteService.deleteAll(lembretesDoUsuario);

        }



        this.usuarioService.saveNewUser(user);

        this.mensagemService.prepareToSave(channel, user, message);

    }
}
