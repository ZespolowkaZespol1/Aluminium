package com.zespolowka.service;

import com.zespolowka.entity.Notification;
import com.zespolowka.entity.user.Role;
import com.zespolowka.entity.user.User;
import com.zespolowka.forms.NewMessageForm;
import com.zespolowka.repository.NotificationRepository;
import com.zespolowka.repository.UserRepository;
import com.zespolowka.service.inteface.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;


@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Notification getNotificationById(Long id) {
        logger.info("getNotificationById = {}", id);
        return notificationRepository.findOne(id);
    }


    @Override
    public Collection<Notification> findTop5ByUserIdOrUserRoleOrderByDateDesc(Long userId, Role userRole) {
        logger.info("findTop5ByUserId={}OrUserRole={}OrderByDateDesc = ", userId, userRole);
        return notificationRepository.findTop5ByUserIdOrUserRoleOrderByDateDesc(userId, userRole);
    }

    @Override
    public Long countByUnreadAndUserId(boolean unread, Long userId) {
        logger.info("countByUnread={}AndUserId={}", unread, userId);
        return notificationRepository.countByUnreadAndUserId(unread, userId);
    }

    @Override
    public Long countByUnreadAndUserRole(boolean unread, Role userRole) {
        logger.info("countByUnread={}AndUserId={}", unread, userRole);
        return notificationRepository.countByUnreadAndUserRole(unread, userRole);
    }

    @Override
    public Notification createNotification(Notification notification) {
        logger.info("createNotif{}", notification);
        return notificationRepository.save(notification);
    }

    public Page<Notification> findAllPageable(Pageable pageable, Long userId, Role userRol) {
        return notificationRepository.findAllByUserIdOrUserRoleOrderByDateDesc(pageable, userId, userRol);
    }

    @Override
    public Notification changeStatus(Long idNotification) {
        Notification notification = notificationRepository.getOne(idNotification);
        notification.setUnread(false);
        return notificationRepository.save(notification);
    }

    public void sendMessage(NewMessageForm form) {
        try {
            String receivers;
            if (form.getReceivers().endsWith(", "))
                receivers = form.getReceivers().substring(0, form.getReceivers().length() - 2);
            else receivers = form.getReceivers();

            String result[] = receivers.split(",");
            Notification notif;
            ArrayList<String> wyslane = new ArrayList<>();
            for (String s : result) {

                String st = s.replaceAll("\\s+", "");
                if (wyslane.contains(st)) {
                    continue;
                }
                if (st.contains("@")) {
                    User usr = userRepository.findUserByEmail(st)
                            .orElseThrow(() -> new NoSuchElementException(String.format("Uzytkownik o emailu =%s nie istnieje", st)));
                    notif = new Notification(form.getMessage(), form.getTopic(), usr.getId(), form.getSender());
                    logger.info("Wiadomosc wyslana do: {}", st);
                    notificationRepository.save(notif);
                    wyslane.add(st);
                } else {
                    String st2 = st.toUpperCase();
                    if (st2.equals(Role.ADMIN.name()) || st2.equals(Role.SUPERADMIN.name()) || st2.equals(Role.USER.name())) {
                        Collection<User> users = userRepository.findUsersByRole(Role.valueOf(st2));
                        for (User usr : users) {
                            notif = new Notification(form.getMessage(), form.getTopic(), usr.getId(), form.getSender());
                            logger.info("Grupowa wiadomosc do: {}", usr.getEmail());
                            notificationRepository.save(notif);
                            wyslane.add(st);
                        }

                    }
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            logger.info(form.toString());
        }
    }

    public void deleteMessagesByUserId(Long id) {
        logger.info("deleteMessagesByUserId");
        logger.info("Przed usunieciem:{}", notificationRepository.count());
        notificationRepository.deleteByUserId(id);
        logger.info("Po usunieciu:{}", notificationRepository.count());
    }

    @Override
    public void deleteMessagesByUserRole(Role userRole) {
        notificationRepository.deleteMessagesByUserRole(userRole);
    }

    @Override
    public void deleteMessagesBySender(User user) {
        notificationRepository.deleteMessagesBySender(user);
    }

    @Override
    public String toString() {
        return "NotificationServiceImpl{" +
                "notificationRepository=" + notificationRepository +
                ", userRepository=" + userRepository +
                '}';
    }
}



