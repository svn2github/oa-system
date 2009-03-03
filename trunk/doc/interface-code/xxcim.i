/* xxcim.i - CIM Load                                                     */
/*************************************************************************/
/* Program ID   : xxfagocim.i                                              */
/* Author       : Yan  -  SCS Information Technology HK Limited.         */
/* Last updated : 10 July, 2004                                          */
/*************************************************************************/
/* Include file for CIMLOAD - CIM GO file                               */
/*************************************************************************/

/*********************/
/* execute_cim_file  */
/*********************/
                    output to  value({1} + ".prn").
                    input from value({1} + ".cim") no-echo.
                    repeat:
                    set m_data m_prg with no-box no-labels with width 80.
                        batchrun = yes.
                        if m_data begins "@@end" then next.
                        if m_data begins "@@batchload" then do:
                            {gprun.i "m_prg"}
                        end.
                    end.
                    input close.
                    output close.

