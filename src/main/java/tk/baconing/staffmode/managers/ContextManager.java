package tk.baconing.staffmode.managers;

import net.luckperms.api.context.ContextCalculator;
import net.luckperms.api.context.ContextConsumer;
import net.luckperms.api.context.ContextSet;
import net.luckperms.api.context.ImmutableContextSet;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ContextManager implements ContextCalculator<Player> {
    @Override
    public void calculate(@NotNull Player target, ContextConsumer contextConsumer) {
        contextConsumer.accept("staffmode", String.valueOf(DatabaseManager.DatabaseQueries.getUser(target).isStaffMode()));
    }

    @Override
    public @NotNull ContextSet estimatePotentialContexts() {
        ImmutableContextSet.Builder builder = ImmutableContextSet.builder();
        builder.add("staffmode", "true");
        builder.add("staffmode", "false");
        return builder.build();
    }
}
