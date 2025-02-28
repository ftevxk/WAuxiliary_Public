package demo.hook.func.test

import android.util.Log
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.type.java.IntType
import com.highcapable.yukihookapi.hook.type.java.LongType
import com.highcapable.yukihookapi.hook.type.java.StringClass
import me.hd.wauxv.data.factory.Wauxv
import me.hd.wauxv.hook.anno.HookAnno
import me.hd.wauxv.hook.anno.ViewAnno
import me.hd.wauxv.hook.base.common.CommonSwitchHooker
import me.hd.wauxv.hook.data.HostInfo

@HookAnno
@ViewAnno
object DebugLogHooker : CommonSwitchHooker() {
    override val funcName = "[测]调试日志"
    override val funcDesc = "将微信应用所有日志调试输出到控制台"

    override fun initOnce() {
        "com.tencent.mars.xlog.Xlog".toClass(HostInfo.appClassLoader).apply {
            method {
                name = "logMonitor"
                param(LongType, IntType, StringClass, StringClass, StringClass, IntType, IntType, LongType, LongType, StringClass)
            }.hook {
                beforeIfEnabled {
                    val level = args(1).int()
                    val tag = args(2).string()
                    val param = args(9).string()
                    when (level) {
                        0 -> Log.v(Wauxv.HOOK_TAG, "$tag: $param")
                        1 -> Log.d(Wauxv.HOOK_TAG, "$tag: $param")
                        2 -> Log.i(Wauxv.HOOK_TAG, "$tag: $param")
                        3 -> Log.w(Wauxv.HOOK_TAG, "$tag: $param")
                        4 -> Log.e(Wauxv.HOOK_TAG, "$tag: $param")
                        5 -> Log.wtf(Wauxv.HOOK_TAG, "$tag: $param")
                    }
                }
            }
        }
    }
}
