/**
 * GokuMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.0  Built on : Jan 18, 2016 (09:41:27 GMT)
 */
package org.apache.ws.axis2;


/**
 *  GokuMessageReceiverInOut message receiver
 */
public class GokuMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutMessageReceiver {
    public void invokeBusinessLogic(
        org.apache.axis2.context.MessageContext msgContext,
        org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault {
        try {
            // get the implementation class for the Web Service
            Object obj = getTheImplementationObject(msgContext);

            GokuSkeleton skel = (GokuSkeleton) obj;

            //Out Envelop
            org.apache.axiom.soap.SOAPEnvelope envelope = null;

            //Find the axisOperation that has been set by the Dispatch phase.
            org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext()
                                                                      .getAxisOperation();

            if (op == null) {
                throw new org.apache.axis2.AxisFault(
                    "Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
            }

            java.lang.String methodName;

            if ((op.getName() != null) &&
                    ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJavaIdentifier(
                            op.getName().getLocalPart())) != null)) {
                if ("comprobarApuesta".equals(methodName)) {
                    org.apache.ws.axis2.ComprobarApuestaResponse comprobarApuestaResponse28 =
                        null;
                    org.apache.ws.axis2.ComprobarApuesta wrappedParam = (org.apache.ws.axis2.ComprobarApuesta) fromOM(msgContext.getEnvelope()
                                                                                                                                .getBody()
                                                                                                                                .getFirstElement(),
                            org.apache.ws.axis2.ComprobarApuesta.class);

                    comprobarApuestaResponse28 = skel.comprobarApuesta(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            comprobarApuestaResponse28, false,
                            new javax.xml.namespace.QName(
                                "http://ws.apache.org/axis2",
                                "comprobarApuestaResponse"));
                } else
                 if ("listarPartidos".equals(methodName)) {
                    org.apache.ws.axis2.ListarPartidosResponse listarPartidosResponse30 =
                        null;
                    org.apache.ws.axis2.ListarPartidos wrappedParam = (org.apache.ws.axis2.ListarPartidos) fromOM(msgContext.getEnvelope()
                                                                                                                            .getBody()
                                                                                                                            .getFirstElement(),
                            org.apache.ws.axis2.ListarPartidos.class);

                    listarPartidosResponse30 = skel.listarPartidos(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            listarPartidosResponse30, false,
                            new javax.xml.namespace.QName(
                                "http://ws.apache.org/axis2",
                                "listarPartidosResponse"));
                } else
                 if ("apostarPartido".equals(methodName)) {
                    org.apache.ws.axis2.ApostarPartidoResponse apostarPartidoResponse32 =
                        null;
                    org.apache.ws.axis2.ApostarPartido wrappedParam = (org.apache.ws.axis2.ApostarPartido) fromOM(msgContext.getEnvelope()
                                                                                                                            .getBody()
                                                                                                                            .getFirstElement(),
                            org.apache.ws.axis2.ApostarPartido.class);

                    apostarPartidoResponse32 = skel.apostarPartido(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            apostarPartidoResponse32, false,
                            new javax.xml.namespace.QName(
                                "http://ws.apache.org/axis2",
                                "apostarPartidoResponse"));
                } else
                 if ("apostarPichichi".equals(methodName)) {
                    org.apache.ws.axis2.ApostarPichichiResponse apostarPichichiResponse34 =
                        null;
                    org.apache.ws.axis2.ApostarPichichi wrappedParam = (org.apache.ws.axis2.ApostarPichichi) fromOM(msgContext.getEnvelope()
                                                                                                                              .getBody()
                                                                                                                              .getFirstElement(),
                            org.apache.ws.axis2.ApostarPichichi.class);

                    apostarPichichiResponse34 = skel.apostarPichichi(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            apostarPichichiResponse34, false,
                            new javax.xml.namespace.QName(
                                "http://ws.apache.org/axis2",
                                "apostarPichichiResponse"));
                } else
                 if ("listarEquipos".equals(methodName)) {
                    org.apache.ws.axis2.ListarEquiposResponse listarEquiposResponse36 =
                        null;
                    org.apache.ws.axis2.ListarEquipos wrappedParam = (org.apache.ws.axis2.ListarEquipos) fromOM(msgContext.getEnvelope()
                                                                                                                          .getBody()
                                                                                                                          .getFirstElement(),
                            org.apache.ws.axis2.ListarEquipos.class);

                    listarEquiposResponse36 = skel.listarEquipos(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            listarEquiposResponse36, false,
                            new javax.xml.namespace.QName(
                                "http://ws.apache.org/axis2",
                                "listarEquiposResponse"));
                } else
                 if ("listarJugadoresEquipo".equals(methodName)) {
                    org.apache.ws.axis2.ListarJugadoresEquipoResponse listarJugadoresEquipoResponse38 =
                        null;
                    org.apache.ws.axis2.ListarJugadoresEquipo wrappedParam = (org.apache.ws.axis2.ListarJugadoresEquipo) fromOM(msgContext.getEnvelope()
                                                                                                                                          .getBody()
                                                                                                                                          .getFirstElement(),
                            org.apache.ws.axis2.ListarJugadoresEquipo.class);

                    listarJugadoresEquipoResponse38 = skel.listarJugadoresEquipo(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            listarJugadoresEquipoResponse38, false,
                            new javax.xml.namespace.QName(
                                "http://ws.apache.org/axis2",
                                "listarJugadoresEquipoResponse"));
                } else {
                    throw new java.lang.RuntimeException("method not found");
                }

                newMsgContext.setEnvelope(envelope);
            }
        } catch (java.lang.Exception e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    //
    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ComprobarApuesta param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ComprobarApuesta.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ComprobarApuestaResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ComprobarApuestaResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ApuestaFinalizada param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ApuestaFinalizada.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ListarPartidos param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ListarPartidos.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ListarPartidosResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ListarPartidosResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ApostarPartido param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ApostarPartido.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ApostarPartidoResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ApostarPartidoResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ApostarPichichi param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ApostarPichichi.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ApostarPichichiResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ApostarPichichiResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ListarEquipos param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ListarEquipos.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ListarEquiposResponse param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ListarEquiposResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ListarJugadoresEquipo param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ListarJugadoresEquipo.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ListarJugadoresEquipoResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ListarJugadoresEquipoResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        org.apache.ws.axis2.ComprobarApuestaResponse param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    org.apache.ws.axis2.ComprobarApuestaResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.ws.axis2.ComprobarApuestaResponse wrapcomprobarApuesta() {
        org.apache.ws.axis2.ComprobarApuestaResponse wrappedElement = new org.apache.ws.axis2.ComprobarApuestaResponse();

        return wrappedElement;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        org.apache.ws.axis2.ListarPartidosResponse param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    org.apache.ws.axis2.ListarPartidosResponse.MY_QNAME, factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.ws.axis2.ListarPartidosResponse wraplistarPartidos() {
        org.apache.ws.axis2.ListarPartidosResponse wrappedElement = new org.apache.ws.axis2.ListarPartidosResponse();

        return wrappedElement;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        org.apache.ws.axis2.ApostarPartidoResponse param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    org.apache.ws.axis2.ApostarPartidoResponse.MY_QNAME, factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.ws.axis2.ApostarPartidoResponse wrapapostarPartido() {
        org.apache.ws.axis2.ApostarPartidoResponse wrappedElement = new org.apache.ws.axis2.ApostarPartidoResponse();

        return wrappedElement;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        org.apache.ws.axis2.ApostarPichichiResponse param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    org.apache.ws.axis2.ApostarPichichiResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.ws.axis2.ApostarPichichiResponse wrapapostarPichichi() {
        org.apache.ws.axis2.ApostarPichichiResponse wrappedElement = new org.apache.ws.axis2.ApostarPichichiResponse();

        return wrappedElement;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        org.apache.ws.axis2.ListarEquiposResponse param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    org.apache.ws.axis2.ListarEquiposResponse.MY_QNAME, factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.ws.axis2.ListarEquiposResponse wraplistarEquipos() {
        org.apache.ws.axis2.ListarEquiposResponse wrappedElement = new org.apache.ws.axis2.ListarEquiposResponse();

        return wrappedElement;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        org.apache.ws.axis2.ListarJugadoresEquipoResponse param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    org.apache.ws.axis2.ListarJugadoresEquipoResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.ws.axis2.ListarJugadoresEquipoResponse wraplistarJugadoresEquipo() {
        org.apache.ws.axis2.ListarJugadoresEquipoResponse wrappedElement = new org.apache.ws.axis2.ListarJugadoresEquipoResponse();

        return wrappedElement;
    }

    /**
     *  get the default envelope
     */
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory) {
        return factory.getDefaultEnvelope();
    }

    private java.lang.Object fromOM(org.apache.axiom.om.OMElement param,
        java.lang.Class type) throws org.apache.axis2.AxisFault {
        try {
            if (org.apache.ws.axis2.ApostarPartido.class.equals(type)) {
                return org.apache.ws.axis2.ApostarPartido.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ApostarPartidoResponse.class.equals(type)) {
                return org.apache.ws.axis2.ApostarPartidoResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ApostarPichichi.class.equals(type)) {
                return org.apache.ws.axis2.ApostarPichichi.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ApostarPichichiResponse.class.equals(type)) {
                return org.apache.ws.axis2.ApostarPichichiResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ApuestaFinalizada.class.equals(type)) {
                return org.apache.ws.axis2.ApuestaFinalizada.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ComprobarApuesta.class.equals(type)) {
                return org.apache.ws.axis2.ComprobarApuesta.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ComprobarApuestaResponse.class.equals(type)) {
                return org.apache.ws.axis2.ComprobarApuestaResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ListarEquipos.class.equals(type)) {
                return org.apache.ws.axis2.ListarEquipos.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ListarEquiposResponse.class.equals(type)) {
                return org.apache.ws.axis2.ListarEquiposResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ListarJugadoresEquipo.class.equals(type)) {
                return org.apache.ws.axis2.ListarJugadoresEquipo.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ListarJugadoresEquipoResponse.class.equals(
                        type)) {
                return org.apache.ws.axis2.ListarJugadoresEquipoResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ListarPartidos.class.equals(type)) {
                return org.apache.ws.axis2.ListarPartidos.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ListarPartidosResponse.class.equals(type)) {
                return org.apache.ws.axis2.ListarPartidosResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }
        } catch (java.lang.Exception e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }

        return null;
    }

    private org.apache.axis2.AxisFault createAxisFault(java.lang.Exception e) {
        org.apache.axis2.AxisFault f;
        Throwable cause = e.getCause();

        if (cause != null) {
            f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
        } else {
            f = new org.apache.axis2.AxisFault(e.getMessage());
        }

        return f;
    }
} //end of class
