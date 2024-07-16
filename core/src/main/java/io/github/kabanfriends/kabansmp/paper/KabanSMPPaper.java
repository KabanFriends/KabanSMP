package io.github.kabanfriends.kabansmp.paper;

import io.github.kabanfriends.kabansmp.core.KabanSMP;

public class KabanSMPPaper extends KabanSMP {

    @Override
    public void onLoad() {
        setPlatform(new PaperPlatform());
        super.onLoad();
    }
}
