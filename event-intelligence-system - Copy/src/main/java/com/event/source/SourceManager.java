package com.event.source;

import java.util.ArrayList;
import java.util.List;

public class SourceManager {

    public static List<EventSource> getSources() {

        List<EventSource> sources = new ArrayList<>();

        // 🔥 Current Sources
        sources.add(new EventbriteSource());
        sources.add(new NasscomSource());

        // 🚀 Future sources (just uncomment when ready)
        // sources.add(new LinkedInSource());
        // sources.add(new BookMyShowSource());
        // sources.add(new MeetupSource());
        // sources.add(new TechCrunchSource());

        return sources;
    }
}