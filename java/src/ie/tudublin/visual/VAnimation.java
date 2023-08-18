package ie.tudublin.visual;

import java.util.ArrayList;

import processing.core.PApplet;

/**
 * VAnimation is a class that is used to handle animating a value over time.
 * Animations are made up of sections. Each section has a duration, start value,
 * end value and an ease function.
 */
public class VAnimation {
    private int lengthMs; // Duration of the animation in milliseconds
    private ArrayList<Section> sections;

    /**
     * Constructor for VAnimation
     *
     * @param lengthMs Duration of the whole animation in milliseconds
     */
    public VAnimation(int lengthMs) {
        this.lengthMs = lengthMs;
        sections = new ArrayList<Section>();
    }

    /**
     * Appends a section to the animation list
     *
     * @param durationMs   Duration of the section in milliseconds
     * @param startValue   Start value of the section
     * @param endValue     End value of the section
     * @param easeFunction Ease function of the section
     */
    public void appendSection(int durationMs, float startValue, float endValue, EaseFunction easeFunction) {
        // If no sections, set start time to 0
        if (sections.size() == 0) {
            sections.add(new Section(0, durationMs, startValue, endValue, easeFunction));
            return;
        }

        // Set start time of next section to end time of last section
        Section lastSection = sections.get(sections.size() - 1);
        int startTime = lastSection.getEndTime();
        sections.add(new Section(startTime, durationMs, startValue, endValue, easeFunction));
    }

    /**
     * Adds a transition to the animation
     *
     * @param startMs      Start time of the transition in milliseconds
     * @param durationMs   Duration of the transition in milliseconds
     * @param startValue   Start value of the transition
     * @param endValue     End value of the transition
     * @param easeFunction Ease function of the transition
     */
    public void addTransition(int startMs, int durationMs, float startValue, float endValue,
            EaseFunction easeFunction) {
        // Check if it overlaps with any existing sections
        for (Section section : sections) {
            if (startMs >= section.getStartTime() && startMs < section.getEndTime())
                throw new RuntimeException("Transition overlaps with existing section");
        }

        if (sections.size() == 0 && startMs != 0) {
            // If no sections, add linear transition from 0 to start value
            appendSection(startMs - 1, startValue, startValue, EaseFunction.easeLinear);
        } else if (sections.size() > 0) {
            // If there are sections, add linear transition from end value of last section
            Section lastSection = sections.get(sections.size() - 1);
            if (startMs != lastSection.getEndTime() + 1) {
                int gapDuration = startMs - lastSection.getEndTime() - 1;
                appendSection(gapDuration, lastSection.getValue(lastSection.getEndTime()), startValue,
                        EaseFunction.easeLinear);
            }
        }
        // Add transition section
        appendSection(durationMs, startValue, endValue, easeFunction);

    }

    /**
     * Returns the value of the animation at the given time
     *
     * @param time
     * @return
     */
    public float getValue(int time) {
        if (time < 0 || time > lengthMs) {
            System.out.println("Time is not within the animation");
        }

        // Search through sections to find the one that contains the time
        for (Section section : sections) {
            if (time >= section.getStartTime() && time < section.getEndTime()) {
                return section.getValue(time);
            }
        }

        System.out.println("Time is not within the animation");
        return 0;
    }

    /**
     * Represents a section of the animation
     */
    class Section {
        private int startTime;
        private int duration;
        private float startValue;
        private float endValue;
        private EaseFunction easeFunction;

        /**
         * Constructor for Section
         *
         * @param startTimeMs  Start time of the section in milliseconds
         * @param durationMs   Duration of the section in milliseconds
         * @param startValue   Start value of the section
         * @param endValue     End value of the section
         * @param easeFunction Ease function of the section
         */
        public Section(int startTimeMs, int durationMs, float startValue, float endValue, EaseFunction easeFunction) {
            this.startTime = startTimeMs;
            this.duration = durationMs;
            this.startValue = startValue;
            this.endValue = endValue;
            this.easeFunction = easeFunction;
        }

        /** @return The start time of the section. */
        public int getStartTime() {
            return startTime;
        }

        /** @return The end time of the section. */
        public int getEndTime() {
            return startTime + duration;
        }

        /**
         * Returns the value of the animation at the given time.
         *
         * @param time
         * @return
         */
        public float getValue(int time) {
            if (time < startTime || time > startTime + duration) {
                throw new RuntimeException("Time is not within the section");
            }

            int relativeTime = time - startTime;

            if (duration == 0)
                return endValue; // Avoid divide by 0 error

            float timePercent = relativeTime / (float) duration;
            float easedTime = easeFunction.ease(timePercent);

            return PApplet.lerp(startValue, endValue, easedTime);
        }
    }
}