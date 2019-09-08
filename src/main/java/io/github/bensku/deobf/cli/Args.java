package io.github.bensku.deobf.cli;

import com.beust.jcommander.Parameter;

class Args {
    
    /**
     * Input mappings file.
     */
    @Parameter(names = {"--maps", "-m"}, required = true)
    public String mappings;
    
    @Parameter(names = {"--format", "-f"}, required = true)
    public String mappingsFormat;
    
    /**
     * Output mappings file.
     */
    @Parameter(names = {"--mapsOut"})
    public String mappingsOut;
    
    @Parameter(names = {"--formatOut"})
    public String outFormat;
    
    /**
     * Reverses the mappings before doing anything with them.
     */
    @Parameter(names = {"--reverse", "-r"})
    public boolean reverse;

}
