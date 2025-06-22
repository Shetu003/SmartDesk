package com.smartdesk.repository;

import com.smartdesk.model.MoodEntry;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to interact with the mood_entries table.
 */
public interface MoodRepository extends JpaRepository<MoodEntry, Long> {
}
