package yee.pltision.blasttech.mixin;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets ="cpw.mods.modlauncher.LaunchServiceHandlerDecorator")
public class MixinLaunchServiceHandlerDecorator {
    /*@Final
    @Shadow
    private DefaultLaunchHandlerService service;

    @Inject(method = "launch",at=@At("HEAD"),cancellable = true)
    private void launch(String[] arguments, ModuleLayer gameLayer, CallbackInfo ci){
        try {
            this.service.launchService(arguments, gameLayer).run();
        } catch (Throwable e) {
//            throw new RuntimeException(e);
        }
        ci.cancel();
    }*/
}
