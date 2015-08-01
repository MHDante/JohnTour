package ca.mixitmedia.johntour;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by MHDante on 2015-07-31.
 */
public class SequencePoint {

    private int id;
    private int nameResID;
    private LatLng coordinates;
    private int bannerResID;
    private int audioResID;
    private int audioSubsResID;
    private int videoResID;
    private int videoSubsResID;

    public int getId() {
        return id;
    }

    public int getNameResID() {
        return nameResID;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public int getBannerResID() {
        return bannerResID;
    }

    public int getAudioResID() {
        return audioResID;
    }

    public int getAudioSubsResID() {
        return audioSubsResID;
    }

    public int getVideoResID() {
        return videoResID;
    }

    public int getVideoSubsResID() {
        return videoSubsResID;
    }

    private SequencePoint() {
    }

    public static ArrayList<SequencePoint> list;

    static {

        list = new ArrayList<>();

        //Intro
        SequencePoint s0 = new SequencePoint();
        s0.id = 0;
        s0.nameResID = R.string.introTitle;
        s0.coordinates = null;
        s0.bannerResID = R.drawable.intro;
        s0.audioResID = R.raw.audio_placeholder;// TODO: R.raw.audio_intro;
        s0.audioSubsResID = R.raw.subs_audio_intro;
        s0.videoResID = R.raw.video_placeholder;// TODO: R.raw.video_loc1;
        s0.videoSubsResID = R.raw.subs_video_intro;
        list.add(s0);

        //Statue
        SequencePoint s1 = new SequencePoint();
        s1.id = 1;
        s1.nameResID = R.string.loc1Title;
        s1.coordinates = new LatLng(43.6607, -79.3909);
        s1.bannerResID = R.drawable.loc1;
        s1.audioResID = R.raw.audio_loc1;
        s1.audioSubsResID = R.raw.subs_audio_loc1;
        s1.videoResID = R.raw.video_placeholder;// TODO: R.raw.video_loc1;
        s1.videoSubsResID = R.raw.subs_video_loc1;
        list.add(s1);

        //EmilyStowe Hospital
        SequencePoint s2 = new SequencePoint();
        s2.id = 2;
        s2.nameResID = R.string.loc2Title;
        s2.coordinates = new LatLng(43.66055, -79.38745);
        s2.bannerResID = R.drawable.loc2;
        s2.audioResID = R.raw.audio_loc2;
        s2.audioSubsResID = R.raw.subs_audio_loc2;
        s2.videoResID = R.raw.video_placeholder;// TODO: R.raw.video_loc2;
        s2.videoSubsResID = R.raw.subs_video_loc2;
        list.add(s2);

        //printer's strike
        SequencePoint s3 = new SequencePoint();
        s3.id = 3;
        s3.nameResID = R.string.loc3Title;
        s3.coordinates = new LatLng(43.66178, -79.38983);
        s3.bannerResID = R.drawable.loc3;
        s3.audioResID = R.raw.audio_loc3;
        s3.audioSubsResID = R.raw.subs_audio_loc3;
        s3.videoResID = R.raw.video_placeholder;// TODO: R.raw.video_loc3;
        s3.videoSubsResID = R.raw.subs_video_loc3;
        list.add(s3);

        //King's College, University
        SequencePoint s4 = new SequencePoint();
        s4.id = 4;
        s4.nameResID = R.string.loc4Title;
        s4.coordinates = new LatLng(43.66111, -79.39433);
        s4.bannerResID = R.drawable.loc4;
        s4.audioResID = R.raw.audio_loc4;
        s4.audioSubsResID = R.raw.subs_audio_loc4;
        s4.videoResID = R.raw.video_placeholder;// TODO: R.raw.video_loc4;
        s4.videoSubsResID = R.raw.subs_video_loc4;
        list.add(s4);


        //Macdonald/Mowat house
        SequencePoint s5 = new SequencePoint();
        s5.id = 5;
        s5.nameResID = R.string.loc5Title;
        s5.coordinates = new LatLng(43.66157, -79.39735);
        s5.bannerResID = R.drawable.loc5;
        s5.audioResID = R.raw.audio_loc5;
        s5.audioSubsResID = R.raw.subs_audio_loc5;
        s5.videoResID = R.raw.video_placeholder;// TODO: R.raw.video_loc5;
        s5.videoSubsResID = R.raw.subs_video_loc5;
        list.add(s5);

        //George Brown House
        SequencePoint s6 = new SequencePoint();
        s6.id = 6;
        s6.nameResID = R.string.loc6Title;
        s6.coordinates = new LatLng(43.65848, -79.39569);
        s6.bannerResID = R.drawable.loc6;
        s6.audioResID = R.raw.audio_loc6;
        s6.audioSubsResID = R.raw.subs_audio_loc6;
        s6.videoResID = R.raw.video_placeholder;// TODO: R.raw.video_loc6;
        s6.videoSubsResID = R.raw.subs_video_loc6;
        list.add(s6);

        //Mackenzie @ 147 Beverly St.
        SequencePoint s7 = new SequencePoint();
        s7.id = 7;
        s7.nameResID = R.string.loc7Title;
        s7.coordinates = new LatLng(43.65453, -79.39411);
        s7.bannerResID = R.drawable.loc7;
        s7.audioResID = R.raw.audio_loc7;
        s7.audioSubsResID = R.raw.subs_audio_loc7;
        s7.videoResID = R.raw.video_placeholder;// TODO: R.raw.video_loc7;
        s7.videoSubsResID = R.raw.subs_video_loc7;
        list.add(s7);

        //Baldwin Ave (Minorities)
        SequencePoint s8 = new SequencePoint();
        s8.id = 8;
        s8.nameResID = R.string.loc8Title;
        s8.coordinates = new LatLng(43.65689, -79.39252);
        s8.bannerResID = R.drawable.loc8;
        s8.audioResID = R.raw.audio_placeholder;// TODO: R.raw.audio_loc8;
        s8.audioSubsResID = R.raw.subs_audio_loc8;
        s8.videoResID = R.raw.video_placeholder;// TODO: R.raw.video_loc8;
        s8.videoSubsResID = R.raw.subs_video_loc8;
        list.add(s8);

        //Back to the Statue
        SequencePoint s9 = new SequencePoint();
        s9.id = 9;
        s9.nameResID = R.string.loc9Title;
        s9.coordinates = new LatLng(43.6607, -79.3909);
        s9.bannerResID = R.drawable.loc9;
        s9.audioResID = R.raw.audio_placeholder;// TODO: R.raw.audio_loc9;
        s9.audioSubsResID = R.raw.subs_audio_loc9;
        s9.videoResID = R.raw.video_placeholder;// TODO: R.raw.video_loc9;
        s9.videoSubsResID = R.raw.subs_video_loc9;
        list.add(s9);

    }

}
