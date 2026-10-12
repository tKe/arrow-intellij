package arrow.intellij

import org.jetbrains.kotlin.analysis.api.KaExperimentalApi
import org.jetbrains.kotlin.analysis.api.KaSession
import org.jetbrains.kotlin.analysis.api.diagnostics.KaDiagnosticWithPsi
import org.jetbrains.kotlin.analysis.api.diagnostics.diagnostics
import org.jetbrains.kotlin.psi.KtElement

/** For IDEs from 263, which replaced the filter-based overload with the composable `KaDiagnostics` query. */
internal object Diagnostics263 : DiagnosticsCompat {
    @OptIn(KaExperimentalApi::class)
    override fun commonDiagnosticsFor(session: KaSession, element: KtElement): Collection<KaDiagnosticWithPsi<*>> =
        context(session) { element.diagnostics().directOnly(true).toList() }
}
