#!/bin/bash
# Script to validate GitHub Actions workflow files

echo "🔍 Validating GitHub Actions workflow files..."

# Check if workflow directory exists
if [ ! -d ".github/workflows" ]; then
    echo "❌ .github/workflows directory not found!"
    exit 1
fi

# Count workflow files
workflow_count=$(find .github/workflows -name "*.yml" -o -name "*.yaml" | wc -l)
echo "📁 Found $workflow_count workflow files"

# Validate each workflow file
errors=0
for file in .github/workflows/*.yml .github/workflows/*.yaml; do
    if [ -f "$file" ]; then
        echo "   Checking $(basename "$file")..."
        
        # Check for required fields
        if ! grep -q "name:" "$file"; then
            echo "      ❌ Missing 'name' field"
            ((errors++))
        fi
        
        if ! grep -q "on:" "$file"; then
            echo "      ❌ Missing 'on' trigger field"
            ((errors++))
        fi
        
        if ! grep -q "jobs:" "$file"; then
            echo "      ❌ Missing 'jobs' field"
            ((errors++))
        fi
        
        # Check for Python/YAML validation if available
        if command -v python3 &> /dev/null; then
            if ! python3 -c "import yaml; yaml.safe_load(open('$file'))" 2>/dev/null; then
                echo "      ❌ Invalid YAML syntax"
                ((errors++))
            else
                echo "      ✅ Valid YAML syntax"
            fi
        fi
    fi
done

# Summary
if [ $errors -eq 0 ]; then
    echo "✅ All workflow files are valid!"
    echo "🚀 Ready for GitHub Actions execution"
else
    echo "❌ Found $errors error(s) in workflow files"
    exit 1
fi