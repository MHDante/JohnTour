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
        int counter = 0;
        SequencePoint s;
        //Intro
        s = new SequencePoint();
        s.id = counter++;//counter++;
        s.nameResID = R.string.introTitle;
        s.coordinates = null;
        s.bannerResID = R.drawable.intro;
        s.audioResID = R.raw.audio_placeholder;// TODO: R.raw.audio_intro;
        s.audioSubsResID = R.raw.subs_audio_intro;
        s.videoResID = R.raw.video_placeholder;// TODO: R.raw.video_loc1;
        s.videoSubsResID = R.raw.subs_video_intro;
        list.add(s);

        //Statue
        s = new SequencePoint();
        s.id = counter++;//1;
        s.nameResID = R.string.loc1Title;
        s.coordinates = new LatLng(43.6607, -79.3909);
        s.bannerResID = R.drawable.loc1;
        s.audioResID = R.raw.audio_loc1;
        s.audioSubsResID = R.raw.subs_audio_loc1;
        s.videoResID = R.raw.video_placeholder;// TODO: R.raw.video_loc1;
        s.videoSubsResID = R.raw.subs_video_loc1;
        list.add(s);

        //EmilyStowe Hospital
        s = new SequencePoint();
        s.id = counter++;//2;
        s.nameResID = R.string.loc2Title;
        s.coordinates = new LatLng(43.66055, -79.38745);
        s.bannerResID = R.drawable.loc2;
        s.audioResID = R.raw.audio_loc2;
        s.audioSubsResID = R.raw.subs_audio_loc2;
        s.videoResID = R.raw.video_placeholder;// TODO: R.raw.video_loc2;
        s.videoSubsResID = R.raw.subs_video_loc2;
        list.add(s);

        //printer's strike
        s = new SequencePoint();
        s.id = counter++;//3;
        s.nameResID = R.string.loc3Title;
        s.coordinates = new LatLng(43.66178, -79.38983);
        s.bannerResID = R.drawable.loc3;
        s.audioResID = R.raw.audio_loc3;
        s.audioSubsResID = R.raw.subs_audio_loc3;
        s.videoResID = R.raw.video_placeholder;// TODO: R.raw.video_loc3;
        s.videoSubsResID = R.raw.subs_video_loc3;
        list.add(s);

        //King's College, University
        s = new SequencePoint();
        s.id = counter++;//4;
        s.nameResID = R.string.loc4Title;
        s.coordinates = new LatLng(43.66111, -79.39433);
        s.bannerResID = R.drawable.loc4;
        s.audioResID = R.raw.audio_loc4;
        s.audioSubsResID = R.raw.subs_audio_loc4;
        s.videoResID = R.raw.video_placeholder;// TODO: R.raw.video_loc4;
        s.videoSubsResID = R.raw.subs_video_loc4;
        list.add(s);


        //Macdonald/Mowat house
        s = new SequencePoint();
        s.id = counter++;//5;
        s.nameResID = R.string.loc5Title;
        s.coordinates = new LatLng(43.66157, -79.39735);
        s.bannerResID = R.drawable.loc5;
        s.audioResID = R.raw.audio_loc5;
        s.audioSubsResID = R.raw.subs_audio_loc5;
        s.videoResID = R.raw.video_placeholder;// TODO: R.raw.video_loc5;
        s.videoSubsResID = R.raw.subs_video_loc5;
        list.add(s);

        //George Brown House
        s = new SequencePoint();
        s.id = counter++;//6;
        s.nameResID = R.string.loc6Title;
        s.coordinates = new LatLng(43.65848, -79.39569);
        s.bannerResID = R.drawable.loc6;
        s.audioResID = R.raw.audio_loc6;
        s.audioSubsResID = R.raw.subs_audio_loc6;
        s.videoResID = R.raw.video_placeholder;// TODO: R.raw.video_loc6;
        s.videoSubsResID = R.raw.subs_video_loc6;
        list.add(s);

        //Mackenzie @ 147 Beverly St.
        s = new SequencePoint();
        s.id = counter++;//7;
        s.nameResID = R.string.loc7Title;
        s.coordinates = new LatLng(43.65453, -79.39411);
        s.bannerResID = R.drawable.loc7;
        s.audioResID = R.raw.audio_loc7;
        s.audioSubsResID = R.raw.subs_audio_loc7;
        s.videoResID = R.raw.video_placeholder;// TODO: R.raw.video_loc7;
        s.videoSubsResID = R.raw.subs_video_loc7;
        list.add(s);

        //Baldwin Ave (Minorities)
        s = new SequencePoint();
        s.id = counter++;//8;
        s.nameResID = R.string.loc8Title;
        s.coordinates = new LatLng(43.65689, -79.39252);
        s.bannerResID = R.drawable.loc8;
        s.audioResID = R.raw.audio_placeholder;// TODO: R.raw.audio_loc8;
        s.audioSubsResID = R.raw.subs_audio_loc8;
        s.videoResID = R.raw.video_placeholder;// TODO: R.raw.video_loc8;
        s.videoSubsResID = R.raw.subs_video_loc8;
        list.add(s);

        //New Sequence Point Kwame Added (Minorities)
        s = new SequencePoint();
        s.id = counter++;
        s.nameResID = R.string.locNativeRelationsTitle;
        s.coordinates = new LatLng(43.65626, -79.39227);
        s.bannerResID = R.drawable.native_relations;
        s.audioResID = R.raw.audio_loc6;// TODO: R.raw.audio_loc8;
        s.audioSubsResID = R.raw.subs_audio_loc6;
        s.videoResID = R.raw.patrice_lookalike;// TODO: R.raw.video_loc8;
        s.videoSubsResID = R.raw.subs_video_patricelookalike;
        list.add(s);


        //Back to the Statue
        s = new SequencePoint();
        s.id = counter++;//9;
        s.nameResID = R.string.loc9Title;
        s.coordinates = new LatLng(43.6607, -79.3909);
        s.bannerResID = R.drawable.loc9;
        s.audioResID = R.raw.audio_placeholder;// TODO: R.raw.audio_loc9;
        s.audioSubsResID = R.raw.subs_audio_loc9;
        s.videoResID = R.raw.video_placeholder;// TODO: R.raw.video_loc9;
        s.videoSubsResID = R.raw.subs_video_loc9;
        list.add(s);

    }

}
