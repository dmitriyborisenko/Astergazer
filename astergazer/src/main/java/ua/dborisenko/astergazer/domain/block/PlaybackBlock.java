package ua.dborisenko.astergazer.domain.block;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Playback")
public class PlaybackBlock extends Block {

    public PlaybackBlock() {
        application = "Playback";
        parametersCount = 2;
    }
}
