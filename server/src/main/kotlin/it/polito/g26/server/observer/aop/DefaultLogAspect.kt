package it.polito.g26.server.observer.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Aspect


@Aspect
class DefaultLogAspect : AbstractLogAspect() {
    @Throws(Throwable::class)
    override fun logInfoAround(joinPoint: ProceedingJoinPoint): Any? {
        return super.logInfoAround(joinPoint)
    }
}