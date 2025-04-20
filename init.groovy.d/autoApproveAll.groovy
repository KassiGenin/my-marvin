import org.jenkinsci.plugins.scriptsecurity.scripts.ScriptApproval
import org.jenkinsci.plugins.scriptsecurity.scripts.PendingScriptApproval

def sap = ScriptApproval.get()

// Approve any pending signatures (method calls, etc.)
sap.getPendingSignatures().each { sig ->
    sap.approveSignature(sig.signature)
}

// Approve any pending inline scripts (entire scripts)
sap.getPendingScripts().each { ps ->
    sap.approveScript(ps.script)
}

sap.save()
println "--> autoApproveAll: all pending script approvals have been granted"
