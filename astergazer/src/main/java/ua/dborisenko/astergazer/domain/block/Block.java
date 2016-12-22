package ua.dborisenko.astergazer.domain.block;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DiscriminatorOptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import ua.dborisenko.astergazer.domain.Script;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Entity
@Inheritance
@DiscriminatorColumn(name = "type")
@DiscriminatorOptions(force = true)
@Table(name = "dlp_block")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(value = StartBlock.class, name = "Start"),
        @Type(value = SipAddHeaderBlock.class, name = "SipAddHeader"),
        @Type(value = SipRemoveHeaderBlock.class, name = "SipRemoveHeader"),
        @Type(value = CustomBlock.class, name = "Custom"), @Type(value = TrueCaseBlock.class, name = "TrueCase"),
        @Type(value = FalseCaseBlock.class, name = "FalseCase"), @Type(value = SwitchBlock.class, name = "Switch"),
        @Type(value = EqualCaseBlock.class, name = "EqualCase"),
        @Type(value = ChecklistBlock.class, name = "Checklist"),
        @Type(value = GotoIfTimeBlock.class, name = "GotoIfTime"), @Type(value = SetBlock.class, name = "Set"),
        @Type(value = SetGlobalBlock.class, name = "SetGlobal"), @Type(value = AnswerBlock.class, name = "Answer"),
        @Type(value = HangupBlock.class, name = "Hangup"), @Type(value = DialBlock.class, name = "Dial"),
        @Type(value = PlaybackBlock.class, name = "Playback"),
        @Type(value = BackgroundBlock.class, name = "Background"), @Type(value = QueueBlock.class, name = "Queue"),
        @Type(value = GotoIfBlock.class, name = "GotoIf"), @Type(value = AgiBlock.class, name = "Agi"),
        @Type(value = BusyBlock.class, name = "Busy"), @Type(value = CongestionBlock.class, name = "Congestion"),
        @Type(value = ProgressBlock.class, name = "Progress"), @Type(value = RingingBlock.class, name = "Ringing"),
        @Type(value = SystemBlock.class, name = "System"), @Type(value = ConfBridgeBlock.class, name = "ConfBridge"),
        @Type(value = MeetMeBlock.class, name = "MeetMe"), @Type(value = MonitorBlock.class, name = "Monitor"),
        @Type(value = MixMonitorBlock.class, name = "MixMonitor"),
        @Type(value = StopMonitorBlock.class, name = "StopMonitor"),
        @Type(value = StopMixMonitorBlock.class, name = "StopMixMonitor"),
        @Type(value = NoCdrBlock.class, name = "NoCdr"), @Type(value = NoOpBlock.class, name = "NoOp"),
        @Type(value = ReadBlock.class, name = "Read"), @Type(value = VoiceMenuBlock.class, name = "VoiceMenu"),
        @Type(value = WaitBlock.class, name = "Wait"), @Type(value = GotoBlock.class, name = "Goto"),
        @Type(value = GosubBlock.class, name = "Gosub"), @Type(value = ReturnBlock.class, name = "Return"),
        @Type(value = MacroBlock.class, name = "Macro"), @Type(value = UserEventBlock.class, name = "UserEvent"),
        @Type(value = MusicOnHoldBlock.class, name = "MusicOnHold"),
        @Type(value = StartMusicOnHoldBlock.class, name = "StartMusicOnHold"),
        @Type(value = StopMusicOnHoldBlock.class, name = "StopMusicOnHold"),
        @Type(value = SayDigitsBlock.class, name = "SayDigits"),
        @Type(value = SayNumberBlock.class, name = "SayNumber"),
        @Type(value = ChannelRedirectBlock.class, name = "ChannelRedirect"),
        @Type(value = ChanSpyBlock.class, name = "ChanSpy"), @Type(value = SendDtmfBlock.class, name = "SendDtmf"),
        @Type(value = RecordBlock.class, name = "Record"), @Type(value = WaitExtenBlock.class, name = "WaitExten"),
        @Type(value = PickupBlock.class, name = "Pickup"), @Type(value = TransferBlock.class, name = "Transfer"),
        @Type(value = AddQueueMemberBlock.class, name = "AddQueueMember"),
        @Type(value = RemoveQueueMemberBlock.class, name = "RemoveQueueMember"),
        @Type(value = DumpChanBlock.class, name = "DumpChan"), @Type(value = DbSetBlock.class, name = "DbSet"),
        @Type(value = DbDelBlock.class, name = "DbDel"), @Type(value = DbDelTreeBlock.class, name = "DbDelTree"),
        @Type(value = AmdBlock.class, name = "Amd")})
public class Block {

    protected static final String COMMAND_PREFIX = "\tsame = n(";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @ManyToOne
    @JoinColumn(name = "script_id")
    @JsonIgnore
    private Script script;

    @Column(name = "local_id")
    private int localId;

    private String caption;

    @Column(insertable = false, updatable = false)
    @JsonIgnore
    private String type;

    @Transient
    protected boolean isSwitcher;

    @Transient
    protected boolean isCaseBlock;

    @Transient
    protected int parametersCount = 0;

    @Transient
    protected String application = "Unknown";

    @Column(name = "is_locked")
    private boolean isLocked;

    @Column(name = "pos_x")
    private int posX;

    @Column(name = "pos_y")
    private int posY;

    @OneToMany(mappedBy = "block", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex")
    private List<BlockParameter> parameters;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Script getScript() {
        return script;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public List<BlockParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<BlockParameter> parameters) {
        for (BlockParameter parameter : parameters) {
            parameter.setBlock(this);
        }
        this.parameters = parameters;
    }

    public boolean getIsSwitcher() {
        return isSwitcher;
    }

    public void setIsSwitcher(boolean isSwitcher) {
        this.isSwitcher = isSwitcher;
    }

    public boolean getIsCaseBlock() {
        return isCaseBlock;
    }

    public void setIsCaseBlock(boolean isCaseBlock) {
        this.isCaseBlock = isCaseBlock;
    }

    protected String buildCommandString(String label, String command) {
        StringBuilder result = new StringBuilder(COMMAND_PREFIX);
        result.append(label);
        result.append("),");
        result.append(command);
        result.append("\n");
        return result.toString();
    }
    
    protected String buildCommandString(String label, String command, String parameters) {
        StringBuilder result = new StringBuilder(COMMAND_PREFIX);
        result.append(label);
        result.append("),");
        result.append(command);
        result.append("(");
        result.append(parameters);
        result.append(")");
        result.append("\n");
        return result.toString();
    }

    protected String buildParametersList(int paramCount) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < paramCount; i++) {
            if (i < getParameters().size()) {
                result.append(getParameters().get(i).getValue());
            }
            if (i != paramCount - 1) {
                result.append(",");
            }
        }
        return result.toString();
    }

    public String translate() {
        return buildCommandString(getLabel(), application, buildParametersList(parametersCount));
    }

    public String translate(List<Block> trueCaseBlocks) {
        return translate();
    }
    
    @JsonIgnore
    public String getLabel() {
        return getCaption();
    }
}
