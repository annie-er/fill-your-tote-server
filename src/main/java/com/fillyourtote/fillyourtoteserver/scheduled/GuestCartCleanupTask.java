package com.fillyourtote.fillyourtoteserver.scheduled;

import com.fillyourtote.fillyourtoteserver.dao.CartItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class GuestCartCleanupTask {

    private static final Logger logger = LoggerFactory.getLogger(GuestCartCleanupTask.class);

    private final CartItemRepository cartItemRepository;

    public GuestCartCleanupTask(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Scheduled(cron = "0 0 0 * * *") // runs every day at midnight
    @Transactional
    public void cleanupExpiredGuestCarts() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(7);
        cartItemRepository.deleteByGuestSessionIdIsNotNullAndCreatedAtBefore(cutoff);
        logger.info("Guest cart cleanup ran at {}. Deleted items older than {}.",
                LocalDateTime.now(), cutoff);
    }
}
